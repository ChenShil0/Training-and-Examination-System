package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * @param score 添加一条成绩记录
     * @return
     */

    int add(Score score);


    List<Score> findAll();

    // 分页

    IPage<Score> findById(Page<?> page, @Param("studentId") Integer studentId);

    /**
     *
     * @return 查询学生的学科分数。
     */

    List<Score> findByExamCode(Integer examCode);


    List<Score> findByExamname(String examname);
}
