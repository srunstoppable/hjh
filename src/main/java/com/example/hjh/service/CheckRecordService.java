package com.example.hjh.service;

import com.example.hjh.entity.CheckRecord;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
public interface CheckRecordService extends IService<CheckRecord> {


    // 老师查询老师-课程-学生相关联的 考勤记录
    public List<CheckRecord> lists(String id);

    //学生查询自己的
    public Response listStu(String id);

    //学生签到
    public Response checkIn(String id, String name, Point2D point2D);


}
