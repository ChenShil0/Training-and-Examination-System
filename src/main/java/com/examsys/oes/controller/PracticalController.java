package com.examsys.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.ApiResult;
import com.examsys.oes.entity.ExamManage;
import com.examsys.oes.entity.Practical;
import com.examsys.oes.serviceimpl.ExamManageServiceImpl;
import com.examsys.oes.serviceimpl.PracticalServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PracticalController {
    @Autowired
    private PracticalServiceImpl practicalService;

    @RequestMapping("/practical")
    public ApiResult findAll(){
        System.out.println("不分页查询所有试卷");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", practicalService.findAll());
        return apiResult;
    }

    @RequestMapping("/practicals")
    public ApiResult findAlls(){
        System.out.println("不分页查询所有试卷");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", practicalService.findAlls());
        return apiResult;
    }

    @RequestMapping("/practicals/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有试卷");
        ApiResult apiResult;
        Page<Practical> practical = new Page<>(page,size);
        IPage<Practical> all = practicalService.findAll(practical);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }

    @RequestMapping("/practical/{practicalCode}")
    public ApiResult findById(@PathVariable("practicalCode") Integer practicalCode){
        System.out.println("根据ID查找");
        Practical res = practicalService.findById(practicalCode);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考试编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功！",res);
    }

    @DeleteMapping("/practical/{practicalCode}")
    public ApiResult deleteById(@PathVariable("practicalCode") Integer practicalCode){
        int res = practicalService.delete(practicalCode);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/practical")
    public ApiResult update(@RequestBody Practical practical){
        int res = practicalService.update(practical);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }

    @PostMapping("/practical")
    public ApiResult add(@RequestBody Practical practical){
        int res =practicalService.add(practical);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    @GetMapping("/practicalTrainingId")
    public ApiResult findOnlyPaperId() {
        Practical res = practicalService.findOnlyTrainingId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }

}
