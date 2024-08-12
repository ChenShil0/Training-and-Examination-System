package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.JudgeQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//判断题

@Mapper
public interface JudgeQuestionMapper {


    List<JudgeQuestion> findByIdAndType(Integer paperId);


    IPage<JudgeQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     * @return JudgeQuestion
     */

    JudgeQuestion findOnlyQuestionId(Integer questionIds);


    int add(JudgeQuestion judgeQuestion);


    List<Integer> findBySubject(@Param("subject") String subject, @Param("pageNo") Integer pageNo);


    int edit(JudgeQuestion judgeQuestion);


    List<JudgeQuestion> findQuestionBySubject(@Param("subject") String subject);

}
