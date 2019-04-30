package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.example.hjh.entity.Question;
import com.example.hjh.mapper.QuestionMapper;
import com.example.hjh.response.Response;
import com.example.hjh.service.QuestionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.hjh.utils.ExcelUtils;
import io.swagger.models.auth.In;
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
 * @since 2019-04-07
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public Response add(Question question) {
        if (insert(question)) {
            return Response.success();
        }
        return Response.fail("操作失败!");
    }

    @Override
    public Response delete(int id) {
        if (deleteById(id)) {
            return Response.success();
        }
        return Response.fail("操作失败!");
    }

    @Override
    public Response update(Question question) {
        if (updateById(question)) {
            return Response.success();
        }
        return Response.fail("操作失败!");
    }

    @Override
    public Response questions(Page<Question> page, String id) {
        Map<Integer, Object> map = new TreeMap<>();
        for (Question question : baseMapper.courses(page, id)) {
//        for (Question question : baseMapper.courses(id)) {
            map.put(question.getId(), question);
//        }
        }
            return Response.success(baseMapper.selectTotal(id)).putAllT(map);
//        return Response.success().putAllT(map);

    }
    @Override
    public Response importQue(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                XSSFRow row = sheet.getRow(row_num);
                Question question = new Question();
                question.setName(ExcelUtils.getStringValue(row.getCell(0)));
                question.setContent(ExcelUtils.getStringValue(row.getCell(1)));
                question.setAnswer(ExcelUtils.getStringValue(row.getCell(2)));
                try {
                    insert(question);
                } catch (Exception e) {
                    continue;
                }
            }
            return Response.success("导入成功");
        } catch (Exception e) {
            return Response.fail("文件格式错误!");
        }
    }

    @Override
    public List<Question> lists(String course) {
        EntityWrapper<Question> ew = new EntityWrapper<>();
        ew.eq("name", course);
        if (selectList(ew) == null) {
            return Collections.emptyList();
        }
        return selectList(ew);
    }

    @Override
    public Question listRand(String course) {
        EntityWrapper<Question> ew = new EntityWrapper<>();
        ew.eq("name", course);
        if (selectList(ew) == null) {
            return null;
        }
        List<Question> list = selectList(ew);

        Question question = list.get(new Random().nextInt(list.size()));
        return question;
    }

    @Override
    public List<Question> getL(String id) {
        if (baseMapper.ques(id).size() == 0) {
            return Collections.emptyList();
        } else {
            return baseMapper.ques(id);
        }
    }

    @Override
    public Question getOne(int id) {
        return selectById(id);
    }
}
