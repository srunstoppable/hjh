package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Question;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
public interface QuestionService extends IService<Question> {

    public Response add(Question question);

    public Response delete(int id);

    public Response update(Question question);

    public Response questions(Page<Question> page, String id);

    public Response importQue(InputStream inputStream);

    //老师指定题目发布，列出所有题目列表
    public List<Question> lists(String course);

    //随机获取题目
    public Question listRand(String course);


    //返回题目列表
    public List<Question> getL(String id);

    public Question getOne(int id);

}
