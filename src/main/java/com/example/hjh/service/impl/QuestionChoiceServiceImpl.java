package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
import com.example.hjh.mapper.QuestionChoiceMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.QuestionChoiceService;
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
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
@Service
public class QuestionChoiceServiceImpl extends ServiceImpl<QuestionChoiceMapper, QuestionChoice> implements QuestionChoiceService {

    @Override
    public Response add(QuestionChoice question) {
        if(insert(question)){
            return Response.success();
        } else return Response.fail("新增失败!");
    }

    @Override
    public Response delete(int id) {
        if(deleteById(id)){
            return Response.success();
        } else return Response.fail("新增失败!");
    }

    @Override
    public Response update(QuestionChoice question) {
        if(updateById(question)){
            return Response.success();
        } else return Response.fail("新增失败!");
    }

    @Override
    public Response questions(Page<QuestionChoice> page, String id) {
        Map<Integer, Object> map = new TreeMap<>();
        for (QuestionChoice question : baseMapper.courses(page, id)) {
            map.put(question.getId(), question);
        }
        return Response.success(baseMapper.selectTotal()).putAllT(map);
    }

    @Override
    public Response importQue(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                XSSFRow row = sheet.getRow(row_num);
                QuestionChoice question = new QuestionChoice();
                question.setName(ExcelUtils.getStringValue(row.getCell(0)));
                question.setContent(ExcelUtils.getStringValue(row.getCell(1)));
                question.setA(ExcelUtils.getStringValue(row.getCell(2)));
                question.setB(ExcelUtils.getStringValue(row.getCell(3)));
                question.setC(ExcelUtils.getStringValue(row.getCell(4)));
                question.setD(ExcelUtils.getStringValue(row.getCell(5)));
                question.setAnswer(ExcelUtils.getStringValue(row.getCell(6)));
                try {
                    insert(question);
                } catch (Exception e) {
                    continue ;
                }
            }
            return Response.success("导入成功");
        } catch (Exception e) {
            return Response.fail("文件格式错误!");
        }
    }

    @Override
    public Response lists(String course) {
        EntityWrapper<QuestionChoice> ew= new EntityWrapper<>();
        ew.eq("name",course);
        if (selectList(ew) == null) {
            return Response.success(Collections.emptyList());
        }
        return Response.success(selectList(ew));


    }

    @Override
    public QuestionChoice listRand(String course) {
        EntityWrapper<QuestionChoice> ew = new EntityWrapper<>();
        ew.eq("name", course);
        if (selectList(ew) == null) {
            return null;
        }
        List<QuestionChoice> list = selectList(ew);

        QuestionChoice question = list.get(new Random().nextInt(list.size()));
        return question;
    }

    @Override
    public List<QuestionChoice> getL(String id) {
        if(baseMapper.choice(id) == null){
            return Collections.emptyList();
        }else return baseMapper.choice(id);
    }

    @Override
    public QuestionChoice getOne(int id) {
        return selectById(id);
    }
}
