package com.examsys.oes.serviceimpl;

import com.examsys.oes.entity.Train;
import com.examsys.oes.mapper.PaperMapper;
import com.examsys.oes.mapper.TrainMapper;
import com.examsys.oes.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainMapper trainMapper;

    @Override
    public List<Train> findAll() {
        return trainMapper.findAll();
    }

    @Override
    public List<Train> findById(Integer trainingId) {
        return trainMapper.findById(trainingId);
    }

    @Override
    public int add(Train train) {
        return trainMapper.add(train);
    }

    @Override
    public void delete(String trainingId, String id) {
        trainMapper.delete(trainingId,id);
    }

    @Override
    public int edit(Train train) {
        return trainMapper.edit(train);
    }

    @Override
    public void deleteByTrainingId(Integer trainingId) {
        trainMapper.deleteByTrainingId(trainingId);
    }
}
