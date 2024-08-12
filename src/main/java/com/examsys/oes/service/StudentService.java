package com.examsys.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.Student;
import com.examsys.oes.entity.Studentwrong;

public interface StudentService {

    IPage<Student> findAll(Page<Student> page, String name, String grade,
                           String tel, String institute, String major, String clazz);

    Student findById(Integer studentId);

    int deleteById(Integer studentId);

    int update(Student student);

    int updatewrong(Studentwrong studentwrong);

    int updatePwd(Student student);
    int add(Student student);
}
