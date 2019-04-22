package com.example.hjh.controller;


import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.ExaminationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */

@Api("套题查询api")
@RestController
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;

    @ApiOperation(value = "学生查询自己对应课程所有要回答的套题", notes = "学生查询自己所有要回答的套题")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/exam")
    public Response get(@RequestParam("name")String name, HttpServletRequest request){
        return Response.success(examinationService.
                list(JWTUtil.getUsername(JWTUtil.getToken(request)),name));
    }


    @ApiOperation(value = "学生查询对应课程可以抢答的套题", notes = "学生查询对应课程可以抢答的套题")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/examNo")
    public Response getNo(@RequestParam("name")String name, HttpServletRequest request){
        return Response.success(examinationService.noStu(name));
    }


}

