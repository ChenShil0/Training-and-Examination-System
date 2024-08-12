package com.examsys.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examsys.oes.entity.ExamManage;
import com.examsys.oes.entity.PaperManage;
import com.examsys.oes.entity.Practical;
import com.examsys.oes.entity.Train;
import com.examsys.oes.mapper.ExamManageMapper;
import com.examsys.oes.mapper.PracticalMapper;
import com.examsys.oes.mapper.TrainMapper;
import com.examsys.oes.service.PaperService;
import com.examsys.oes.service.PracticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticalServiceImpl implements PracticalService {


    @Autowired
    private PracticalMapper practicalMapper;

    @Autowired
    private TrainMapper trainMapper;




    @Override
    public List<Practical> findAll() {
        Page<Practical> practical = new Page<>(0,9999);
        List<Practical> practicalList = practicalMapper.findAll(practical).getRecords();
        return practicalList;
    }

    @Override
    public List<Practical> findAlls() {
        Page<Practical> practical = new Page<>(0,9999);
        List<Practical> practicalList = practicalMapper.findAll(practical).getRecords();
        return practicalList;
    }

    @Override
    public IPage<Practical> findAll(Page<Practical> page) {
        IPage<Practical> iPage = practicalMapper.findAll(page);
        return iPage;
    }

    @Override
    public Practical findById(Integer practicalCode) {
        Practical practical = practicalMapper.findById(practicalCode);
        practical.setTotalScore(100);
        return practical;
    }

    @Override
    public int delete(Integer practicalCode) {

        Practical practical = practicalMapper.findById(practicalCode);
        if(practical == null) {
            return 0;
        }
        trainMapper.deleteByTrainingId(practical.getTrainingId());
        return practicalMapper.delete(practicalCode);
    }

    @Override
    public int update(Practical practical) {
        return practicalMapper.update(practical);
    }

    @Override
    public int add(Practical practical) {
        return practicalMapper.add(practical);
    }

    @Override
    public Practical findOnlyTrainingId() {
        return practicalMapper.findOnlyTraingingId();
    }
}
