package com.examsys.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.FillQuestion;
import com.examsys.oes.mapper.FillQuestionMapper;
import com.examsys.oes.service.FillQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionServiceImpl implements FillQuestionService {

    @Autowired
    private FillQuestionMapper fillQuestionMapper;

    @Override
    public List<FillQuestion> findByIdAndType(Integer paperId) {
        return fillQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public IPage<FillQuestion> findAll(Page<FillQuestion> page) {
        return fillQuestionMapper.findAll(page);
    }

    @Override
    public FillQuestion findOnlyQuestionId(Integer questionIds) {
        return fillQuestionMapper.findOnlyQuestionId(questionIds);
    }

    @Override
    public int add(FillQuestion fillQuestion) {
        return fillQuestionMapper.add(fillQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return fillQuestionMapper.findBySubject(subject,pageNo);
    }

    @Override
    public int edit(FillQuestion fillQuestion) {
        return fillQuestionMapper.edit(fillQuestion);
    }

    @Override
    public List<FillQuestion> findBySubject(String subject) {
        return fillQuestionMapper.findQuestionBySubject(subject);
    }
}
