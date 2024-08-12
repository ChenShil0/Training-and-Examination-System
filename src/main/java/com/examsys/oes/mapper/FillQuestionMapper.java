package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.FillQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//填空题
@Mapper
public interface FillQuestionMapper {


    List<FillQuestion> findByIdAndType(Integer paperId);


    IPage<FillQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return FillQuestion
     */

    FillQuestion findOnlyQuestionId(Integer questionIds);


    int add(FillQuestion fillQuestion);


    List<Integer> findBySubject(@Param("subject") String subject, @Param("pageNo") Integer pageNo);


    int edit(FillQuestion fillQuestion);


    List<FillQuestion> findQuestionBySubject(@Param("subject") String subject);

}
