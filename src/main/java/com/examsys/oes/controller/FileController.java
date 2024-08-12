package com.examsys.oes.controller;

import com.examsys.oes.entity.ApiResult;
import com.examsys.oes.entity.UserFile;
import com.examsys.oes.serviceimpl.FileServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.apache.catalina.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileController {
    @Autowired
    private FileServiceImpl fileService;

    @Value("${spring.servlet.multipart.location}")
    private String destPath;


    @PostMapping("/upload")
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file){
        Map<String, String> res = new HashMap<>();
        System.out.println(destPath);
        try{
            UUID randomUUID = UUID.randomUUID();
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            long size = file.getSize();
            String type = file.getContentType();
            // 根据日期生成目录
            String localContainer = "/pdfs";
            String uploadPath = ResourceUtils.getURL(destPath).getPath()+localContainer;
            String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dateDirPath = new File(uploadPath+File.separator+dateFormat);
            if(!dateDirPath.exists()){
                dateDirPath.mkdirs();
            }

            file.transferTo(new File(dateDirPath, fileName));
            // 将文件信息存入数据库
            UserFile fileManager = new UserFile();
           fileManager.setFileName(fileName);
            fileManager.setExt('.'+extension);
            fileManager.setPath(Paths.get(localContainer, dateFormat, fileName).toString());
            fileManager.setSize((int)size);
            fileManager.setType(type);
            fileManager.setTrainId(String.valueOf(randomUUID));

           fileService.save(fileManager);

            res.put("code", "0");
            res.put("msg", "上传成功");

        }catch(IOException e){
            res.put("code", "-1");
            res.put("msg", "上传失败");
        }
        return res;
    }

    @GetMapping("download/{trainId}")
    public void download(@PathVariable("trainId") String trainId, HttpServletResponse response){
        String openStyle = "attachment";
        try{
            getFile(openStyle, trainId, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getFile(String openStyle, String id, HttpServletResponse response) throws Exception {
        UserFile userFile = fileService.queryByTrainId(id);
        String realPath = ResourceUtils.getURL(destPath).getPath()+userFile.getPath();

        FileInputStream is = new FileInputStream(new File(realPath));
        // 附件下载
        response.setHeader("content-disposition", openStyle+";filename=" + URLEncoder.encode(userFile.getFileName(), "UTF-8"));
        // 获取响应response输出流
        ServletOutputStream os = response.getOutputStream();
        // 文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);

    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @GetMapping("file/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        try {
            UserFile fileInfo = fileService.queryByUserFileId(id);
            final String realPath = ResourceUtils.getURL("classpath").getPath() + fileInfo.getPath();
            File file = new File(realPath);
            if (file.exists()) {
                file.delete();  //立即删除
            }
           fileService.delete(id);
            map.put("code", 0);
            map.put("msg", "删除成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "删除成功！");
        }
        return map;
    }

    @GetMapping("file/delete")
    @ResponseBody
    public void delete() throws FileNotFoundException {
        int max = fileService.findMax();
        UserFile fileInfo = fileService.queryByUserFileId(max);
        final String realPath = ResourceUtils.getURL("classpath").getPath() + fileInfo.getPath();
        File file = new File(realPath);
        if (file.exists()) {
            file.delete();  //立即删除
        }
        fileService.delete(max);
    }
    @GetMapping("/files/{trainId}")
    public ApiResult<UserFile> findById(@PathVariable("trainId") String trainId) {
        UserFile file = fileService.queryByTrainId(trainId);
        ApiResult res =  ApiResultHandler.buildApiResult(200,"请求成功",file);
        return  res;
    }


    @GetMapping("preview/{trainId}")
    public void preview(@PathVariable("trainId") String trainId, HttpServletResponse response) throws Exception {
        String openStyle = "inline";
        getFile(openStyle,trainId,response);
    }
}
