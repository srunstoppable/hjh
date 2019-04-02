package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.entity.Userinfo;
import com.example.hjh.response.Response;
import com.example.hjh.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
@Api(tags = "课程api")
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    private CourseService courseService;


    @ApiOperation(value = "跟新/指定课程的教师", notes = "指定课程的教师")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PutMapping("/update")
    public Response update(@RequestBody Course course) {
        return courseService.appoint(course);
    }

    @ApiOperation(value = "批量导入课程信息", notes = "批量导入课程信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/import")
    public Response importFile(@RequestParam("file") MultipartFile file) throws IOException {
        return courseService.courseAll(file.getInputStream());
    }


    @ApiOperation(value = "新增课程信息", notes = "新增导入课程信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/add")
    public Response add(@RequestBody Course course) {
        return courseService.addCourse(course);
    }


    @ApiOperation(value = "删除课程信息", notes = "删除课程信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @DeleteMapping("/delete")
    public Response add(@RequestParam("id") int id) {
        return courseService.delete(id);
    }

    @ApiOperation(value = "分页查询课程信息", notes = "分页查询课程信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/list")
    public Response list() {
        Integer i[] = getPageSizeFromGetRequest();
        Page<Course> page = new Page<>(i[0], i[1]);
        return courseService.courses(page);
    }

}

