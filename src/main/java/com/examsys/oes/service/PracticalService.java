package com.examsys.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.ExamManage;
import com.examsys.oes.entity.Practical;

import java.util.List;

public interface PracticalService {

    List<Practical> findAll();
    List<Practical> findAlls();
    IPage<Practical> findAll(Page<Practical> page);

    Practical findById(Integer practicalCode);

    int delete(Integer practicalCode);

    int update(Practical practical);

    int add(Practical practical);

    Practical findOnlyTrainingId();
}
