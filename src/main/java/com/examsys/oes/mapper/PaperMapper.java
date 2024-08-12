package com.examsys.oes.mapper;

import com.examsys.oes.entity.PaperManage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaperMapper {

    List<PaperManage> findAll();


    List<PaperManage> findById(Integer paperId);


    int add(PaperManage paperManage);


    void delete(@Param("paperId") String paperId, @Param("type") String type, @Param("questionId") String questionId);

    /**
     * 根据试卷id删除题目关联
     *
     * @param paperId 试卷id
     */

    void deleteByPaperId(@Param("paperId") Integer paperId);
}
