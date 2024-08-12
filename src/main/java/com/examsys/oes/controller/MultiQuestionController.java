package com.examsys.oes.controller;

import com.examsys.oes.entity.ApiResult;
import com.examsys.oes.entity.MultiQuestion;
import com.examsys.oes.serviceimpl.MultiQuestionServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId/{questionIds}")
    public ApiResult findOnlyQuestion(@PathVariable("questionIds") Integer questionIds) {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId(questionIds);
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/MultiQuestion")
    public ApiResult add(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.add(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @PostMapping("/editMultiQuestion")
    public ApiResult edit(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.edit(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(200,"修改成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"修改失败",res);
    }
}
