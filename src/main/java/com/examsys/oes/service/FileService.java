package com.examsys.oes.service;


import com.examsys.oes.entity.UserFile;
import org.apache.catalina.User;

public interface FileService {

    public UserFile queryByTrainId(String trainId);

    int save(UserFile file);

    public UserFile queryByUserFileId(Integer id);

    int update(UserFile file);

    int delete(int id);

     int findMax();
}
