package com.examsys.oes.controller;

import com.examsys.oes.entity.ApiResult;
import com.examsys.oes.entity.JudgeQuestion;
import com.examsys.oes.serviceimpl.JudgeQuestionServiceImpl;
import com.examsys.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JudgeQuestionController {

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @PostMapping("/judgeQuestion")
    public ApiResult add(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.add(judgeQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/judgeQuestionId/{questionIds}")
    public ApiResult findOnlyQuestionId(@PathVariable("questionIds") Integer questionIds) {
        JudgeQuestion res = judgeQuestionService.findOnlyQuestionId(questionIds);
        return  ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/editJudgeQuestion")
    public ApiResult edit(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.edit(judgeQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"修改成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"修改失败",res);
    }
}
