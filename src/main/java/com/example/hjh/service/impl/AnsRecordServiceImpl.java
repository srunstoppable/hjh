package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.AnsRecord;
import com.example.hjh.mapper.AnsRecordMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.AnsRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
@Service
public
class AnsRecordServiceImpl extends ServiceImpl<AnsRecordMapper, AnsRecord> implements AnsRecordService {



    @Override
    public List<AnsRecord> listWeb(String id) {
        EntityWrapper<AnsRecord> ew = new EntityWrapper<>();
        ew.eq("promulgator", id);
        List<AnsRecord> list = selectList(ew);
        if (list.size() == 0) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }

    @Override
    public List<AnsRecord>listTea(String course,String id) {
        EntityWrapper<AnsRecord> ew = new EntityWrapper<>();
        ew.eq("promulgator", id);
        ew.eq("course_name",course);
        List<AnsRecord> list = selectList(ew);
        if (list.size() == 0) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }

    @Override
    public Response listStu(String course,String id) {
        EntityWrapper<AnsRecord> ew = new EntityWrapper<>();
        ew.eq("stu_id", id);
        ew.eq("course_name",course);
        List<AnsRecord> list = selectList(ew);
        if (list .size() == 0) {
            return Response.success(Collections.emptyList());
        } else {
            return Response.success(list);
        }
    }

    @Override
    public Response add(AnsRecord ansRecord) {
        if(insert(ansRecord)){

            return Response.success(ansRecord);
        }
        return Response.fail("失败1");
    }

    @Override
    public Response update(AnsRecord ansRecord) {
        if(updateById(ansRecord)){
            return Response.success();
        }else {
            return Response.fail("失败!");
        }
    }
}
