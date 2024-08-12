package com.examsys.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.JudgeQuestion;

import java.util.List;

public interface JudgeQuestionService {

    List<JudgeQuestion> findByIdAndType(Integer paperId);

    IPage<JudgeQuestion> findAll(Page<JudgeQuestion> page);

    JudgeQuestion findOnlyQuestionId(Integer questionIds);

    int add(JudgeQuestion judgeQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);

    int edit(JudgeQuestion judgeQuestion);

    List<JudgeQuestion> findBySubject(String subject);

}
