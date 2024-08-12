package com.examsys.oes.serviceimpl;

import com.examsys.oes.entity.UserFile;
import com.examsys.oes.mapper.FileMapper;
import com.examsys.oes.service.FileService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public UserFile queryByTrainId(String trainId) {
        return fileMapper.queryByTrainId(trainId);
    }

    @Override
    public int save(UserFile file) {
        return  fileMapper.save(file);
    }

    @Override
    public UserFile queryByUserFileId(Integer id) {
        return fileMapper.queryByUserFileId(id);
    }

    @Override
    public int update(UserFile file) {
        return fileMapper.update(file);
    }

    @Override
    public int delete(int id) {
        return fileMapper.delete(id);
    }

    @Override
    public int findMax() {
        return fileMapper.findMax();
    }
}
