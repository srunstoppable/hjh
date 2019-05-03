package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.File;
import com.example.hjh.mapper.FileMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.FileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Response files(String id) {
        EntityWrapper<File>ew=new EntityWrapper<>();
        Map<Integer,Object> map = new HashMap<>();
        ew.eq("promulgator",id);
        List<File> list = selectList(ew);
        for (File file :list){
            map.put(file.getId(),file);
        }
        return Response.success().putAllT(map);
    }

    public List<File> wxFiles(String id) {
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

    @Override
    public Response deleteFile(int id) {
        if(deleteById(id)){
            return Response.success();
        }
        return Response.fail();
    }
}
