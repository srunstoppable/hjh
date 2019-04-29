package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.CourseStu;
import com.example.hjh.mapper.CourseStuMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.CourseStuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.utils.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
@Service
public class CourseStuServiceImpl extends ServiceImpl<CourseStuMapper, CourseStu> implements CourseStuService {

    @Override
    public Response list(Page<CourseStu> page, String id) {
        Map<Integer, Object> map = new TreeMap<>();
        for (CourseStu courseStu : baseMapper.getAll(page, id)) {
            map.put(courseStu.getId(), courseStu);
        }
        return Response.success(baseMapper.count(id)).putAllT(map);
    }

    @Override
    public Response imports(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                XSSFRow row = sheet.getRow(row_num);
                CourseStu courseStu = new CourseStu();
                courseStu.setCourse(ExcelUtils.getStringValue(row.getCell(0)));
                courseStu.setUserid(ExcelUtils.getStringValue(row.getCell(1)));
                try {
                    insert(courseStu);
                } catch (Exception e) {
                    return Response.fail("数据重复!");
                }
            }
            return Response.success("导入成功");
        } catch (Exception e) {
            return Response.fail("文件格式错误!");
        }
    }

    @Override
    public Response add(CourseStu courseStu) {
        try {
            if (insert(courseStu)) {
                return Response.success();
            }
        } catch (Exception e) {
            return Response.fail("新增失败，请输入正确的课程名/学号");
        }
        return Response.fail("新增失败");
    }

    @Override
    public Response delete(int id) {
        if (deleteById(id)) {
            return Response.success();
        }
        return Response.fail();
    }

    @Override
    public Response update(CourseStu courseStu) {
        if (updateById(courseStu)) {
            return Response.success();
        }
        return Response.fail();
    }


}
