package com.examsys.oes.mapper;

import com.examsys.oes.entity.Admin;
import com.examsys.oes.entity.Student;
import com.examsys.oes.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    public Admin adminLogin(@Param("username") Integer username, @Param("password") String password);


    public Teacher teacherLogin(@Param("username") Integer username, @Param("password") String password);


    public Student studentLogin(@Param("username") Integer username, @Param("password") String password);
}
