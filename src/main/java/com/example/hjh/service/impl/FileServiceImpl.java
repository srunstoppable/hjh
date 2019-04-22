package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.File;
import com.example.hjh.mapper.FileMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.FileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-14
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Override
    public boolean add(File file) {
        return insert(file);
    }

    @Override
    public File getFile(int id) {
        return selectById(id);
    }

    @Override
    public List<File> files(String id) {
        EntityWrapper<File>ew=new EntityWrapper<>();
        ew.eq("promulgator",id);
        return selectList(ew);
    }

    @Override
    public List<File> lists(String name) {
        EntityWrapper<File>ew=new EntityWrapper<>();
        ew.eq("course",name);
        return selectList(ew);
    }
}
