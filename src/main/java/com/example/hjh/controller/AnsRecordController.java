package com.example.hjh.controller;


import com.example.hjh.entity.AnsRecord;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.AnsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@Api("答题记录api")
@RestController
@RequestMapping("/ansRecord")
public class AnsRecordController {
    @Autowired
    private AnsRecordService ansRecordService;


    @ApiOperation(value = "教师查询答题记录", notes = "教师查询答题记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/teacher")
    public Response getT(HttpServletRequest request) {
        Map<Integer,Object> map =  new TreeMap<>();
        for (AnsRecord ansRecord:ansRecordService.listTea(JWTUtil.getUsername(JWTUtil.getToken(request)))){
            map.put(ansRecord.getId(),ansRecord);
        }
        return Response.success().putAllT(map);
    }
    @ApiOperation(value = "教师查询答题记录/微信端", notes = "教师查询答题记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/teacher/wx")
    public Response getTwx(HttpServletRequest request) {
        return Response.success(ansRecordService.listTea(JWTUtil.getUsername(JWTUtil.getToken(request))));
    }

    @ApiOperation(value = "学生查询答题记录", notes = "学生查询答题记录", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/student")
    public Response getS(HttpServletRequest request) {
        return ansRecordService.listStu(JWTUtil.getUsername(JWTUtil.getToken(request)));
    }


}

