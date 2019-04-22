package com.example.hjh.controller;


import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-14
 */
@Api("文件相关api")
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/upload")
    public Response upload(@RequestBody MultipartFile file, @RequestParam("course") String course, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        String savePath = "/usr/local";
        String contentType = file.getContentType();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String imageName = contentType.substring(contentType.indexOf("/") + 1);
        String fileName = uuid + "." + imageName;
        File f = new File(savePath);
        com.example.hjh.entity.File file1 = new com.example.hjh.entity.File();
        file1.setAccess("/usr/local/" + fileName).setCourse(course)
                .setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request)))
                .setName(fileName);
        if (!f.exists()) {
            f.mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(f.getPath().concat("/").concat(fileName));) {
            int b = 0;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
            outputStream.flush();
            fileService.add(file1);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return Response.success("上传成功");
    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/download")
    public void download(@RequestParam("id") int id, HttpServletResponse response) {
        try {
            com.example.hjh.entity.File file = fileService.getFile(id);
            String path = file.getAccess();
            File file1 = new File(path);
            InputStream fis = new BufferedInputStream(new FileInputStream(file1));
            String filename = file1.getName();
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            String fileName = new String(filename.getBytes("gb2312"), "iso8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.write(buffer);
            ouputStream.flush();
            ouputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "学生获取文件列表", notes = "学生获取文件列表")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/student")
    public Response getFile(@RequestParam("course")String name){
        return Response.success(fileService.lists(name));
    }
    @ApiOperation(value = "教师获取文件列表", notes = "教师获取文件列表")
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/files")
    public Response files(HttpServletRequest request){
        return Response.success(fileService.files(JWTUtil.getUsername(JWTUtil.getToken(request))));
    }
}

