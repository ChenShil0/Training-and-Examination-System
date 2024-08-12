package com.examsys.oes.serviceimpl;

import com.examsys.oes.entity.Admin;
import com.examsys.oes.entity.Student;
import com.examsys.oes.entity.Teacher;
import com.examsys.oes.mapper.LoginMapper;
import com.examsys.oes.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Admin adminLogin(Integer username, String password) {
        return loginMapper.adminLogin(username,password);
    }

    @Override
    public Teacher teacherLogin(Integer username, String password) {
        return loginMapper.teacherLogin(username,password);
    }

    @Override
    public Student studentLogin(Integer username, String password) {
        return loginMapper.studentLogin(username,password);
    }
}
