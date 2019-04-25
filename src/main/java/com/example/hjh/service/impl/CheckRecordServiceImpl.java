package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

    private static final double EARTH_RADIUS = 6378.137; // 平均半径,单位M

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
    public Response checkIn(String id, String name, double x1, double y1) {
        Date now = new Date();
        if(!isCheck(id,name,now)){
            return  Response.fail("你已签到!");
        }
        Course course = courseService.course(name);
        if (course.getOpen() == null || course.getOpen() == "N") {
            return Response.fail("对不起，未开放签到功能!");
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCourse(name).setUserid(id);

        if(getDistace(course.getLongitude(),course.getLatitude(),x1,y1) >10){
            return Response.fail("不在签到范围内");
        }
        checkRecord.setDate(now);
        Date end = course.getBegin();
        double time = now.getTime() - end.getTime();
        double minute = time/(1000 * 60);
        if (minute > 10) {
            checkRecord.setLate("迟到");
        } else {
            checkRecord.setLate("准时");
        }
        insert(checkRecord);
        return Response.success();
    }

    @Override
    public boolean isCheck(String id, String name,Date date) {
        Course course = courseService.course(name);
        List<CheckRecord> l =baseMapper.reCheck(date,id,name);
        if(l.size()>0){
            return false;
        }
        return true;
    }

    public static double getDistace(double x, double y, double x1, double y1) {
        double radLat1 = getRadian(y);
        double radLat2 = getRadian(y1);
        double a = radLat1 - radLat2;
        double b = getRadian(x)- getRadian(x1);
        double s = 2* Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1) * Math.cos(radLat2)* Math.pow(Math.sin(b / 2), 2)));
        s = s *EARTH_RADIUS;
        return s * 1000;
    }

    public static double getRadian(double degree){
        return degree*Math.PI/180.0;
    }
}
