package com.example.hjh.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.CourseStu;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.io.InputStream;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
public interface CourseStuService extends IService<CourseStu> {

    //分页
    public Response list(Page<CourseStu>page,String id);

    //导入
    public Response imports(InputStream inputStream);

    //新增
    public Response add(CourseStu courseStu);

    //删除
    public Response delete(int id);

    //更新
    public Response update(CourseStu courseStu);

}
