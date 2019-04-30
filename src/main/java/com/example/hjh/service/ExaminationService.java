package com.example.hjh.service;

import com.example.hjh.entity.Examination;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.entity.condition.ExamChange;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
public interface ExaminationService extends IService<Examination> {

    public boolean add(Examination examination);

    public Examination getExam(String id);

    public List<ExamChange> list(String id, String name);

    public List<Examination> noStu(String name);
}
