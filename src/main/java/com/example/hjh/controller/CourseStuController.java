package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.entity.CourseStu;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.CourseStuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */

@Api("课程-学生关联 api")
@RestController
@RequestMapping("/courseStu")
public class CourseStuController extends BaseController{

    @Autowired
    private CourseStuService courseStuService;

    @ApiOperation(value = "批量导入课程信息", notes = "批量导入课程信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/import")
    public Response importFile(@RequestParam("file") MultipartFile file) throws IOException {
        return courseStuService.imports(file.getInputStream());
    }

    @ApiOperation(value = "新增课程-学生关联", notes = "新增课程-学生关联")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/add")
    public Response add(@RequestBody CourseStu courseStu) {
        return courseStuService.add(courseStu);
    }


    @ApiOperation(value = "删除课程-学生关联", notes = "删除课程-学生关联")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @DeleteMapping("/delete")
    public Response add(@RequestParam("id") int id) {
        return courseStuService.delete(id);
    }

    @ApiOperation(value = "分页课程-学生关联信息", notes = "分页课程-学生关联信息")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/list")
    public Response list(HttpServletRequest request) {
        Integer i[] = getPageSizeFromGetRequest();
        Page<CourseStu> page = new Page<>(i[0], i[1]);
        return courseStuService.list(page,JWTUtil.getUsername(JWTUtil.getToken(request)));
    }
    @ApiOperation(value = "更新课程-学生关联", notes = "更新课程-学生关联")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PutMapping("/update")
    public Response update(@RequestBody CourseStu courseStu) {
        return courseStuService.update(courseStu);
    }




}

