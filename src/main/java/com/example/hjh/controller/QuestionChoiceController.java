package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.impl.QuestionChoiceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
@Api("题库 选择题 api")
@RestController
@RequestMapping("/questionChoice")
public class QuestionChoiceController extends BaseController{

    @Autowired
    private QuestionChoiceServiceImpl questionChoiceService;

    @ApiOperation(value = "批量导入问题", notes = "批量导入问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/import")
    public Response importQue(@RequestParam("file") MultipartFile file) throws Exception {
        return questionChoiceService.importQue(file.getInputStream());
    }

    @ApiOperation(value = "新增问题", notes = "新增问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/add")
    public Response add(@RequestBody QuestionChoice question) {
        return questionChoiceService.add(question);
    }


    @ApiOperation(value = "删除问题", notes = "删除问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @DeleteMapping("/delete")
    public Response delete(@RequestParam("id") int id) {
        return questionChoiceService.delete(id);
    }

    @ApiOperation(value = "更新问题", notes = "删除问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PutMapping("/update")
    public Response update(@RequestBody QuestionChoice question) {
        return questionChoiceService.update(question);
    }


    @ApiOperation(value = "分页查询老师所拥有的课程问题", notes = "分页查询老师所拥有的课程问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/list")
    public Response update(HttpServletRequest request) {
        Integer i[] = getPageSizeFromGetRequest();
        Page<QuestionChoice> page = new Page<>(i[0], i[1]);
        return questionChoiceService.questions(page, JWTUtil.getUsername(JWTUtil.getToken(request)));
    }


}

