package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.QuestionChoice;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
public interface QuestionChoiceService extends IService<QuestionChoice> {
    public Response add(QuestionChoice question);

    public Response delete(int id);

    public Response update(QuestionChoice question);

    public Response questions(Page<QuestionChoice> page, String id);

    public Response importQue(InputStream inputStream);

    //老师指定题目发布，列出所有题目列表
    public List<QuestionChoice> lists(String course);

    //随机获取题目
    public QuestionChoice listRand(String course);


    //返回题目列表
    public List<QuestionChoice> getL(String id);

    public QuestionChoice getOne(int id);


}
