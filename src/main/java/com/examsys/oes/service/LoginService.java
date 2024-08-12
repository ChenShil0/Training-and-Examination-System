package com.examsys.oes.service;

import com.examsys.oes.entity.Admin;
import com.examsys.oes.entity.Student;
import com.examsys.oes.entity.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Teacher teacherLogin(Integer username, String password);

    public Student studentLogin(Integer username, String password);
}
