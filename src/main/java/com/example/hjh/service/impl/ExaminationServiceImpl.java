package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.Examination;
import com.example.hjh.entity.condition.ExamChange;
import com.example.hjh.mapper.ExaminationMapper;
import com.example.hjh.service.ExaminationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination> implements ExaminationService {

    @Override
    public boolean add(Examination examination) {
        return insert(examination);
    }

    @Override
    public Examination getExam(String id) {
        return selectById(id);
    }

    @Override
    public List<ExamChange> list(String id, String name) {
        List<ExamChange> list = new ArrayList<>();
        EntityWrapper<Examination>ew = new EntityWrapper<>();
        ew.eq("stu_id",id);
        ew.eq("course",name);
        if(selectList(ew) == null){
            return Collections.emptyList();
        }else {
            for (Examination examination:selectList(ew)){
                    ExamChange examChange = new ExamChange();
                    examChange.setCourse(examination.getCourse())
                            .setId(examination.getId())
                            .setPromulgator(examination.getPromulgator())
                            .setType(examination.getType());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                examChange.setTime(simpleDateFormat.format(examination.getTime()));
                list.add(examChange);
            }
            return list;
        }
    }

    @Override
    public List<Examination> noStu(String name) {
            return baseMapper.exams(name);

    }
}
