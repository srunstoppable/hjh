package com.example.hjh.controller;

import com.example.hjh.entity.CheckRecord;
import com.example.hjh.entity.condition.Jw;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.CheckRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
@Api("签到记录api")
@RestController
@RequestMapping("/checkRecord")
public class CheckRecordController {

    @Autowired
    private CheckRecordService checkRecordService;

    @ApiOperation(value = "老师查询所有的考勤记录", notes = "老师查询所有的考勤记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/teacher")
    public Response getT(HttpServletRequest request) {
        Map<Integer ,Object> map  = new TreeMap<>();
        for(CheckRecord checkRecord :checkRecordService.lists(JWTUtil.getUsername(JWTUtil.getToken(request)))){
            map.put(checkRecord.getId(),checkRecord);
        }
        return Response.success().putAllT(map);
    }

    @ApiOperation(value = "老师查询对应课程的考勤记录", notes = "老师查询对应课程的考勤记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/teacher/wx")
    public Response getTwx(@RequestParam("course")String course) {
        return Response.success(checkRecordService.listsTo(course));
    }


    @ApiOperation(value = "学生查询对应课程的考勤记录", notes = "学生查询对应课程的考勤记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/stu")
    public Response getS(@RequestParam("course")String course,HttpServletRequest request) {
        return checkRecordService.listStu(JWTUtil.getUsername(JWTUtil.getToken(request)),course);
    }

    @ApiOperation(value = "学生签到", notes = "学生签到", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/checkIn")
    public Response check(@RequestBody Jw jw, HttpServletRequest request) {
        return Response.success(checkRecordService.checkIn(JWTUtil.getUsername(JWTUtil.getToken(request)), jw.getName(),jw.getLongitude(),jw.getLatitude()));
    }


}

