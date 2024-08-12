package com.examsys.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.MultiQuestion;
import com.examsys.oes.mapper.MultiQuestionMapper;
import com.examsys.oes.service.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {

    @Autowired
    private MultiQuestionMapper multiQuestionMapper;
    @Override
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }

    @Override
    public IPage<MultiQuestion> findAll(Page<MultiQuestion> page) {
        return multiQuestionMapper.findAll(page);
    }

    @Override
    public MultiQuestion findOnlyQuestionId(Integer questionIds) {
        return multiQuestionMapper.findOnlyQuestionId(questionIds);
    }

    @Override
    public int add(MultiQuestion multiQuestion) {
        return multiQuestionMapper.add(multiQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubject(subject,pageNo);
    }

    @Override
    public int edit(MultiQuestion multiQuestion) {
        return multiQuestionMapper.edit(multiQuestion);
    }

    @Override
    public List<MultiQuestion> findBySubject(String subject) {
        return multiQuestionMapper.findQuestionBySubject(subject);
    }
}
