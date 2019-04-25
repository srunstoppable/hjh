package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.mapper.CourseMapper;
import com.example.hjh.response.Response;
import com.example.hjh.response.Result;
import com.example.hjh.service.CourseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.utils.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    //更新，指定
    @Override
    public Response appoint(Course course) {
        updateAllColumnById(course);
        return Response.success();
    }


    /**
     * 导入形式按照数据表形式  id,namne,所属教师userid
     *
     * @param inputStream
     * @return
     */
    @Override
    public Response courseAll(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                XSSFRow row = sheet.getRow(row_num);
                Course course = new Course();
                course.setName(ExcelUtils.getStringValue(row.getCell(0)));
                course.setUserid(ExcelUtils.getStringValue(row.getCell(1)));
                try {
                    insert(course);
                } catch (Exception e) {
                    return Response.fail("数据重复!");
                }
            }
            return Response.success("导入成功");
        } catch (Exception e) {
            return Response.fail("文件格式错误!");
        }

    }


    //新增单个
    @Override
    public Response addCourse(Course course) {
        try {
            insert(course);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("请确定该职工号是否存在");
        }

    }

    @Override
    public Response delete(int id) {
        deleteById(id);
        return Response.success();
    }

    @Override
    public Response courses(Page<Course> page) {
        Map<Integer, Object> map = new TreeMap();
        for (Course course : baseMapper.courses(page)) {
            map.put(course.getId(), course);
        }
        return Response.success(baseMapper.selectTotal()).putAllT(map);
    }


    @Override
    public Result setTime(String name) {
        Result result = new Result();
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("name", name);
        Course course = selectOne(ew);
        if (course.getBegin() == null || course.getEnd() == null) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public Result open(String name) {
        Result result = new Result();
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("name", name);
        Course course = selectOne(ew);
        if (course.getOpen() == null || course.getOpen() == "N") {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public Response startOpen(String name, double x ,double y) {
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("name", name);
        Course course = selectOne(ew);
        course.setBegin(new Date());
        course.setLatitude(x);
        course.setLongitude(y);
        course.setOpen("Y");
        updateById(course);
        return Response.success();
    }

    @Override
    public Response close(String name) {
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("name", name);
        Course course = selectOne(ew);
        if (course.getEnd() == null || course.getOpen() == null) {
            return Response.fail("未定义签到时间！");
        }
        course.setOpen("N");
        updateById(course);
        return Response.success();
    }

    @Override
    public List<Course> getCourse(String id) {
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("userid", id);
        return selectList(ew);
    }

    @Override
    public Response getScourse(String id) {
        return Response.success(baseMapper.courses(id));
    }

    @Override
    public Course course(String name) {
        EntityWrapper<Course> ew = new EntityWrapper<>();
        ew.eq("name", name);
        return selectOne(ew);
    }
}
