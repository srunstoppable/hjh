package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.Examination;
import com.example.hjh.entity.condition.ExamChange;
import com.example.hjh.mapper.ExaminationMapper;
import com.example.hjh.service.ExaminationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserinfoService userinfoService;

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
        ew.orderBy("time");
        if(selectList(ew) == null){
            return Collections.emptyList();
        }else {
            int i =1;
            for (Examination examination:selectList(ew)){
                    ExamChange examChange = new ExamChange();
                    examChange.setCourse(examination.getCourse())
                            .setId(examination.getId())
                            .setPromulgator(userinfoService.gerName(examination.getPromulgator()))
                            .setType(examination.getType())
                            .setSerId(i);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                examChange.setTime(simpleDateFormat.format(examination.getTime()));
                list.add(examChange);
                i++;
            }
            return list;
        }
    }

    @Override
    public List<ExamChange> noStu(String name) {
        List<ExamChange> examChanges = new ArrayList<>();
        List<Examination> list = baseMapper.exams(name);
        if(list.size() !=0){
            int i =1;
            for (Examination examination:list){
                ExamChange examChange = new ExamChange();
                examChange.setCourse(examination.getCourse()).setSerId(i)
                        .setId(examination.getId())
                        .setPromulgator(userinfoService.gerName(examination.getPromulgator()))
                        .setType(examination.getType());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                examChange.setTime(simpleDateFormat.format(examination.getTime()));
                examChanges.add(examChange);
                i++;
            }

        }
            return examChanges;

    }
}
