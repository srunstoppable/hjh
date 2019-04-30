package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Userinfo;
import com.example.hjh.mapper.UserinfoMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.UserinfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.utils.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements UserinfoService {

    @Override
    public Userinfo login(Userinfo user) {
        EntityWrapper<Userinfo> ew = new EntityWrapper<>();
        ew.eq("id", user.getId());
        ew.eq("password", user.getPassword());
        return selectOne(ew);
    }

    @Override
    public Userinfo getUser(String id) {
        return selectById(id);
    }


    /**
     * 批量导入形式  id name password iden（身份）
     *
     * @param inputStream
     * @return
     */
    @Override
    public Response userAll(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                XSSFRow row = sheet.getRow(row_num);
                Userinfo userinfo = new Userinfo();
                userinfo.setId(ExcelUtils.getStringValue(row.getCell(0)))
                        .setName(ExcelUtils.getStringValue(row.getCell(1)))
                        .setPassword(ExcelUtils.getStringValue(row.getCell(2)))
                        .setIden(ExcelUtils.getStringValue(row.getCell(3)));
                try {
                    insert(userinfo);
                } catch (Exception e) {
                    return Response.fail("数据重复或错误!");
                }
            }
            return Response.success();
        } catch (Exception e) {
            return Response.fail("文件格式错误!");
        }
    }

    @Override
    public Response add(Userinfo userinfo) {
        try {
            insert(userinfo);
            return Response.success();
        } catch (Exception e) {
            return Response.fail("请确认是否有该用户信息!");
        }

    }

    @Override
    public Response users(Page<Userinfo> page) {
        Map<String, Object> map = new TreeMap<>();
        for (Userinfo userinfo : baseMapper.users(page)) {
            map.put(userinfo.getId(), userinfo);
        }
        return Response.success(baseMapper.selectTotal()).putAll(map);
    }

    @Override
    public Response delete(String id) {
        deleteById(id);
        return Response.success();
    }

    @Override
    public Response update(Userinfo userinfo) {
        updateById(userinfo);
        return Response.success();
    }

    @Override
    public Response students(String course) {
        return Response.success(baseMapper.student(course));
    }

    @Override
    public List<Userinfo> stuRand(String course) {
        List<Userinfo> list = baseMapper.student(course);
        List<Userinfo> userinfolist = new ArrayList<>();
        if (list == null) {
            return null;
        }
        int size = new Random().nextInt(list.size() + 1);
        while (size == 0) {
            size = new Random().nextInt(list.size() + 1);
        }
        Set<Userinfo> set = new HashSet<>();
        while (set.size() < size) {
            set.add(list.get(new Random().nextInt(list.size())));
        }
        userinfolist.addAll(set);
        return userinfolist;

    }
}
