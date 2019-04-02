package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.io.InputStream;

/**
 * <p>
 *  服务类
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



}


