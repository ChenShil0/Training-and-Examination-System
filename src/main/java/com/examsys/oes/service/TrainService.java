package com.examsys.oes.service;

import com.examsys.oes.entity.PaperManage;
import com.examsys.oes.entity.Train;

import java.util.List;

public interface TrainService {

    List<Train> findAll();

    List<Train> findById(Integer trainingId);

    int add(Train train);

    void delete(String trainingId, String id);

    int edit(Train train);
    void deleteByTrainingId(Integer trainingId);
}
