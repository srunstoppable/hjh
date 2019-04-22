package com.example.hjh.service;

import com.example.hjh.entity.AnsRecord;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
public interface AnsRecordService extends IService<AnsRecord> {

    public List<AnsRecord> listTea(String id);

    public Response listStu(String id);

    public Response add(AnsRecord ansRecord);

    public Response update(AnsRecord ansRecord);
}
