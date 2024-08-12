package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.ExamManage;
import com.examsys.oes.entity.Practical;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PracticalMapper {


    IPage<Practical> findAll(Page page);


    IPage<Practical> findAlls(Page page);


    Practical findById(Integer practicalCode);


    int delete(Integer practicalCode);


    int update(Practical practical);


    int add(Practical practical);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     * @return paperId
     */

    Practical  findOnlyTraingingId();
}
