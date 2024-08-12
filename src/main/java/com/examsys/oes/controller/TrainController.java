package com.examsys.oes.controller;

import com.examsys.oes.entity.*;
import com.examsys.oes.serviceimpl.FileServiceImpl;
import com.examsys.oes.serviceimpl.PaperServiceImpl;
import com.examsys.oes.serviceimpl.PracticalServiceImpl;
import com.examsys.oes.serviceimpl.TrainServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainController {

    @Autowired
    private TrainServiceImpl trainService;

    @Autowired
    private PracticalServiceImpl practicalService;

    @Autowired
    private FileServiceImpl fileService;

    @GetMapping("/trains")
    public ApiResult<Train> findAll() {
        ApiResult res =  ApiResultHandler.buildApiResult(200,"请求成功",trainService.findAll());
        return  res;
    }

    @GetMapping("/trains/{practicalCode}")
    public ApiResult<Train> findById(@PathVariable("practicalCode") Integer practicalCode) {
        Practical practical = practicalService.findById(practicalCode);
        ApiResult res =  ApiResultHandler.buildApiResult(200,"请求成功",trainService.findById(Integer.valueOf(practical.getTrainingId())));
        return  res;
    }

    @PostMapping("/train")
    public ApiResult add(@RequestBody Train train) {
        int id = fileService.findMax();
        UserFile file = fileService.queryByUserFileId(id);
        train.setId(file.getTrainId());
        int res = trainService.add(train);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }


    @GetMapping("/train/delete/{trainingId}/{id}")
    public ApiResult delete(
            @PathVariable("trainingId") String trainingId,
            @PathVariable("id") String id
    ) {
        trainService.delete(trainingId, id);
        return ApiResultHandler.buildApiResult(200,"删除成功", null);
    }

    @PostMapping("/training")
    public ApiResult edit(@RequestBody Train train) {
        int res =trainService.edit(train);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"修改成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"修改失败",res);
    }
}
