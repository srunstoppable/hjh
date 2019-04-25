package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;
import com.example.hjh.response.Result;

import java.awt.geom.Point2D;
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
public interface CourseService extends IService<Course> {

    //指定课程的任课教师
    public Response appoint(Course course);

    //批量导入课程信息
    public Response courseAll(InputStream inputStream);

    //新增课程
    public Response addCourse(Course course);

    //删除
    public Response delete(int id);

    //分页查询
    public Response courses(Page<Course> page);

    //判单课程是否已经定义签到时间
    //useless
    public Result setTime(String name);

    //判断课程是否开放签到
    //useless
    public Result open(String name);


    //老师开启签到
    public Response startOpen(String name, double x,double y);


    //关闭签到
    public Response close(String name);

    //教师在选择课程签到的时候列出所有课程让其选择
    public List<Course> getCourse(String id);

    //学生在签到时也要列出所有的课程让其选择
    public Response getScourse(String id);


    //根据名字获取单个课程
    public Course course(String name);


}


