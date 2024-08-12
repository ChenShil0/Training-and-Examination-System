package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.FillQuestion;
import com.examsys.oes.entity.JudgeQuestion;
import com.examsys.oes.entity.MultiQuestion;
import com.examsys.oes.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AnswerMapper {

    IPage<AnswerVO> findAll(Page<AnswerVO> page, @Param("subject") String subject, @Param("section") String section, @Param("question") String question);


    /**
     * 查选择题
     * @param questionId 选择题id
     * @return 选择题
     */

    MultiQuestion findMultiQuestionById(Long questionId);

    /**
     * 查填空题
     *
     * @param questionId 题目id
     * @return 填空题
     */

    FillQuestion findFillQuestionById(Long questionId);

    /**
     * 查判断题
     *
     * @param questionId 题目id
     * @return 判断题
     */

    JudgeQuestion findJudgeQuestionById(Long questionId);
}
