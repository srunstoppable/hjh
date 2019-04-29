package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.Examination;
import com.example.hjh.mapper.ExaminationMapper;
import com.example.hjh.service.ExaminationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    public List<Examination> list(String id, String name) {
        EntityWrapper<Examination>ew = new EntityWrapper<>();
        ew.eq("stu_id",id);
        ew.eq("course",name);
        if(selectList(ew) == null){
            return Collections.emptyList();
        }else {
            return selectList(ew);
        }
    }

    @Override
    public List<Examination> noStu(String name) {
            return baseMapper.exams(name);

    }
}
