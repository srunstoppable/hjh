package com.example.hjh.service;

import com.example.hjh.entity.File;
import com.baomidou.mybatisplus.service.IService;
import com.example.hjh.response.Response;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-14
 */
public interface FileService extends IService<File> {
    public boolean add(File file);

    public File getFile(int id);

    public Response files(String id);

    public List<File> lists(String name);

    public List<File> wxFiles(String id);

    public Response deleteFile(int id);

}
