package com.examsys.oes.mapper;

import com.examsys.oes.entity.Replay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplayMapper {


    List<Replay> findAll();


    List<Replay> findAllById(Integer messageId);


    Replay findById(Integer messageId);


    int delete(Integer replayId);


    int update(Replay replay);


    int add(Replay replay);
}
