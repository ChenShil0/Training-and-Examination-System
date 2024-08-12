package com.examsys.oes.controller;

import com.examsys.oes.entity.ApiResult;
import com.examsys.oes.entity.FillQuestion;
import com.examsys.oes.serviceimpl.FillQuestionServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FillQuestionController {

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;

    @PostMapping("/fillQuestion")
    public ApiResult add(@RequestBody FillQuestion fillQuestion) {
        int res = fillQuestionService.add(fillQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/fillQuestionId/{questionIds}")
    public ApiResult findOnlyQuestionId(@PathVariable("questionIds") Integer questionIds) {
        FillQuestion res = fillQuestionService.findOnlyQuestionId(questionIds);
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/editFillQuestion")
    public ApiResult edit(@RequestBody FillQuestion fillQuestion) {
        int res = fillQuestionService.edit(fillQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"修改成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"修改失败",res);
    }
}
