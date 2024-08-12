package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.MultiQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//选择题
@Mapper
public interface MultiQuestionMapper {
    /**
     * select * from multiquestions where questionId in (
     * 	select questionId from papermanage where questionType = 1 and paperId = 1001
     * )
     */

    List<MultiQuestion> findByIdAndType(Integer PaperId);


    IPage<MultiQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     * @return MultiQuestion
     * @Select("select questionId from multi_question order by questionId desc limit 1")
     *     MultiQuestion findOnlyQuestionId();
     */

    MultiQuestion findOnlyQuestionId(Integer questionIds);


    int add(MultiQuestion multiQuestion);


    List<Integer> findBySubject(@Param("subject") String subject, @Param("pageNo") Integer pageNo);


    int edit(MultiQuestion multiQuestion);


    List<MultiQuestion> findQuestionBySubject(@Param("subject") String subject);
}
