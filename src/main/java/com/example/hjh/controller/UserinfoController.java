package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Userinfo;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.response.Result;
import com.example.hjh.service.UserinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api("用户api")
@RestController
@RequestMapping("/userinfo")
public class UserinfoController extends BaseController {

    @Autowired
    private UserinfoService userinfoService;

    @ApiOperation(value = "登录返回token", notes = "登录返回token")
    @PostMapping("/login")
    public Result login(@RequestBody Userinfo userinfo) {
        Result result = new Result();
        if (userinfoService.login(userinfo) != null) {
            result.setSuccess(true);
            result.setData(userinfoService.login(userinfo));
            result.setToken(JWTUtil.sign(userinfo.getId(), userinfo.getPassword()));
            return result;
        }
        result.setSuccess(false);
        return result;
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/add")
    public Response add(@RequestBody Userinfo userinfo) {
        return userinfoService.add(userinfo.setIden("教师"));
    }


    @ApiOperation(value = "批量导入", notes = "批量导入")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/import")
    public Response addAll(@RequestParam("file") MultipartFile file) throws IOException {
        return userinfoService.userAll(file.getInputStream());
    }

    @ApiOperation(value = "分页查询教师学生信息", notes = "分页查询教师学生信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/list")
    public Response list() {
        Integer i[] = getPageSizeFromGetRequest();
        Page<Userinfo> page = new Page<>(i[0], i[1]);
        return userinfoService.users(page);
    }

    @ApiOperation(value = "删除教师信息", notes = "删除教师信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @DeleteMapping("/delete")
    public Response delete(@RequestParam("id") String id) {
        return userinfoService.delete(id);
    }


    @ApiOperation(value = "更新教师信息", notes = "更新教师信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PutMapping("/update")
    public Response delete(@RequestBody Userinfo userinfo) {
        return userinfoService.update(userinfo);
    }


    @ApiOperation(value = "师指定学生回答题目，列出上该门课程的所有学生", notes = "师指定学生回答题目，列出上该门课程的所有学生")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/students")
    public Response getStu(@RequestParam("courseName") String name) {
        return userinfoService.students(name);
    }


}

