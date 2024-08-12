package com.examsys.oes.mapper;

import com.examsys.oes.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {


    public List<Admin> findAll();


    public Admin findById(Integer adminId);


    public int deleteById(int adminId);



    public int update(Admin admin);


    public int add(Admin admin);
}
