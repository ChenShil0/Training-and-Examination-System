package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.ExamManage;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ExamManageMapper {
//    @Select("select * from exam_manage")
//    List<ExamManage> findAll();


    IPage<ExamManage> findAll(Page page);


    IPage<ExamManage> findAlls(Page page);


    ExamManage findById(Integer examCode);


    int delete(Integer examCode);


    int update(ExamManage exammanage);


    int add(ExamManage exammanage);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     * @return paperId
     */

    ExamManage findOnlyPaperId();
}
