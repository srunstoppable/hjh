package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.entity.Userinfo;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
public interface UserinfoService extends IService<Userinfo> {
    public Userinfo login(Userinfo user);

    public Userinfo getUser(String id);

    //批量导入
    public Response userAll(InputStream inputStream);

    //单个新增
    public Response add(Userinfo userinfo);

    //分页查询
    public Response users(Page<Userinfo> page);

    //删除信息
    public Response delete(String id);

    //更新信息
    public Response update(Userinfo userinfo);

    //老师指定学生回答题目，需要列出上该门课程的所有学生
    public Response students(String course);

    //老师随机一个学生回答题目，必须时对应课程的学生
    //TODO
    public List<Userinfo> stuRand(String course);

    public String gerName(String id);

}
