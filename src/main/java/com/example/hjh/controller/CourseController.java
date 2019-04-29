package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.entity.Userinfo;
import com.example.hjh.entity.condition.Jw;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
@Api("课程api")
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

    @ApiOperation(value = "老师开启签到", notes = "开启签到", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/open")
    public Response open(@RequestBody Jw jw) {
       return courseService.startOpen(jw.getName(),jw.getLatitude(),jw.getLongitude());
    }




    @ApiOperation(value = "老师开启签到时选择课程列表", notes = "老师开启签到时选择课程列表", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/Tcourse")
    public Response getTc(HttpServletRequest request) {
        return Response.success(courseService.getCourse(JWTUtil.getUsername(JWTUtil.getToken(request))));
    }

    @ApiOperation(value = "学生签到时选择课程列表", notes = "学生签到时选择课程列表", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/Scourse")
    public Response getSc(HttpServletRequest request) {
        return courseService.getScourse(JWTUtil.getUsername(JWTUtil.getToken(request)));
    }

    @ApiOperation(value = "老师查询自己的课程", notes = "老师查询自己的课程", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/obtain")
    public Response getC(HttpServletRequest request) {
        Map<Integer, Object> map = new TreeMap<>();
        for (Course course : courseService.getCourse(JWTUtil.getUsername(JWTUtil.getToken(request)))) {
            map.put(course.getId(), course);
        }
        return Response.success().putAllT(map);

    }

    @ApiOperation(value = "老师关闭签到", notes = "老师关闭签到", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/close")
    public Response close(@RequestParam("name") String name) {
        return courseService.close(name);
    }


}

