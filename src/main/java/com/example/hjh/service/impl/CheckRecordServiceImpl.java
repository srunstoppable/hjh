package com.example.hjh.service.impl;

import com.example.hjh.entity.CheckRecord;
import com.example.hjh.entity.Course;
import com.example.hjh.mapper.CheckRecordMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.CheckRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord> implements CheckRecordService {

    private static final double EARTH_RADIUS = 6371393; // 平均半径,单位M

    @Autowired
    private CourseService courseService;

    @Override
    public List<CheckRecord> lists(String id) {
        if (baseMapper.get(id) == null) {
            return Collections.emptyList();
        }
        return baseMapper.get(id);
    }

    @Override
    public Response listStu(String id) {
        if (baseMapper.getStuRecord(id) == null) {
            return Response.success(Collections.emptyList());
        }
        return Response.success(baseMapper.getStuRecord(id));
    }


    @Override
    public Response checkIn(String id, String name, Point2D point2D) {
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCourse(name).setUserid(id);
        Course course = courseService.course(name);
        Date now = new Date();
        if (course.getOpen() == null || course.getOpen() == "N") {
            return Response.fail("对不起，未开放签到功能!");
        }
        if(getDistace(course.getLongitude(),course.getLatitude(),point2D) >10){
            return Response.fail("不在签到范围内");
        }

        checkRecord.setDate(now);
        Date end = course.getBegin();
        double time = now.getTime() - end.getTime();
        double minute = time/(1000 * 60);
        if (time > 10) {
            checkRecord.setLate("迟到");
        } else {
            checkRecord.setLate("准时");
        }
        return Response.success();
    }

    public static double getDistace(double x, double y, Point2D point2D) {
        double cos = Math.cos(y) * Math.ceil(point2D.getY()) * Math.cos(x - point2D.getX())
                + Math.sin(y) * Math.sin(point2D.getY());
        double acos = Math.acos(cos);
        return EARTH_RADIUS * acos;
    }

}
