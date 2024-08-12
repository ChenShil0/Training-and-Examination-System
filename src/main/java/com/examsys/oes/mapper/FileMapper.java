package com.examsys.oes.mapper;

import com.examsys.oes.entity.UserFile;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

import java.io.File;
import java.util.List;

@Mapper
public interface FileMapper {



    public UserFile queryByTrainId(String trainId);


    int  save(UserFile file);


    public UserFile queryByUserFileId(Integer id);


    int update(UserFile file);


    int delete(int id);


     int findMax();
}
