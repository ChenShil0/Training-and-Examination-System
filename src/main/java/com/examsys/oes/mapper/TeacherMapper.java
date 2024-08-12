package com.examsys.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.Teacher;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeacherMapper {


    IPage<Teacher>  findAll(Page page);

//    @Select("select * from teacher")
//    public List<TeacherMapper.xml> findAll();


    public Teacher findById(Integer teacherId);


    public int deleteById(Integer teacherId);


    public int update(Teacher teacher);


    public int add(Teacher teacher);
}
