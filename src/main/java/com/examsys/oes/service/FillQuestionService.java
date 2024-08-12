package com.examsys.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {

    List<FillQuestion> findByIdAndType(Integer paperId);

    IPage<FillQuestion> findAll(Page<FillQuestion> page);

    FillQuestion findOnlyQuestionId(Integer questionIds);

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);

    int edit(FillQuestion fillQuestion);

    List<FillQuestion> findBySubject(String subject);
}
