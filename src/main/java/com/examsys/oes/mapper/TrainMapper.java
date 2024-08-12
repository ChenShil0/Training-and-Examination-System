package com.examsys.oes.mapper;

import com.examsys.oes.entity.FillQuestion;
import com.examsys.oes.entity.PaperManage;
import com.examsys.oes.entity.Train;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainMapper {


    List<Train> findAll();


    List<Train> findById(Integer trainingId);


    int add(Train train);


    void delete(@Param("trainingId") String trainingId, @Param("id") String id);


    void deleteByTrainingId(@Param("trainingId") Integer trainingId);


    int edit(Train train);


}
