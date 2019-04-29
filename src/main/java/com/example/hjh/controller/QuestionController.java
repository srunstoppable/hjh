package com.example.hjh.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.*;
import com.example.hjh.entity.condition.*;
import com.example.hjh.jwt.JWTUtil;
import com.example.hjh.response.Response;
import com.example.hjh.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */

@Api("题库 判断题 api")
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AnsRecordService ansRecordService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private QuestionChoiceService questionChoiceService;


    @ApiOperation(value = "批量导入问题", notes = "批量导入问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/import")
    public Response importQue(@RequestParam("file") MultipartFile file) throws Exception {
        return questionService.importQue(file.getInputStream());
    }

    @ApiOperation(value = "新增问题", notes = "新增问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/add")
    public Response add(@RequestBody Question question) {
        return questionService.add(question);
    }


    @ApiOperation(value = "删除问题", notes = "删除问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @DeleteMapping("/delete")
    public Response delete(@RequestParam("id") int id) {
        return questionService.delete(id);
    }

    @ApiOperation(value = "更新问题", notes = "删除问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PutMapping("/update")
    public Response update(@RequestBody Question question) {
        return questionService.update(question);
    }


    @ApiOperation(value = "分页查询老师所拥有的课程问题", notes = "分页查询老师所拥有的课程问题", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/list")
    public Response update(HttpServletRequest request) {
        Integer i[] = getPageSizeFromGetRequest();
        Page<Question> page = new Page<>(i[0], i[1]);
        return questionService.questions(page, JWTUtil.getUsername(JWTUtil.getToken(request)));
    }

    /**
     * ***************************************重要功能点*******************8
     */

    @ApiOperation(value = "指定题目发布，指定学生", notes = "指定题目发布，指定学生", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pub")
    public Response publish(@RequestBody ConditionEntity conditionEntity, HttpServletRequest request) {

        List<String> list = conditionEntity.getList();
        List<Question> list1 = conditionEntity.getQuestions();
        List<QuestionChoice> list2 = conditionEntity.getQuestionChoices();
        if (list != null) {
            for (String user : list) {
                Examination examination = new Examination();
                String eid = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
                examination.setId(eid).setStuId(user).setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request)))
                        .setTime(new Date()).setCourse(conditionEntity.getName()).setType("指定");
                examinationService.add(examination);
                //-----end  插入套题记录

                //----start 插图套-题关联
                for (Question question : list1) {
                    Contact contact = new Contact();
                    contact.setExamId(eid).setType(question.getType()).setQuestionId(question.getId());
                    contactService.add(contact);
                }
                for (QuestionChoice questionChoice : list2) {
                    Contact contact = new Contact();
                    contact.setQuestionId(questionChoice.getId()).setType(questionChoice.getType()).setExamId(eid);
                    contactService.add(contact);
                }
            }
        }
        return Response.success("发布成功!");
    }

    @ApiOperation(value = "指定题目发布，随机学生", notes = "指定题目发布，随机学生", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pubT")
    public Response publishT(@RequestBody ConditionEntity conditionEntity, HttpServletRequest request) {
        List<Userinfo> list = userinfoService.stuRand(conditionEntity.getName());
        List<Question> list1 = conditionEntity.getQuestions();
        List<QuestionChoice> list2 = conditionEntity.getQuestionChoices();
        if (list != null) {
            for (Userinfo userinfo : list) {
                Examination examination = new Examination();
                String eid = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
                examination.setId(eid).setStuId(userinfo.getId()).setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request)))
                        .setTime(new Date()).setCourse(conditionEntity.getName()).setType("随机");
                examinationService.add(examination);
                //-----end  插入套题记录
                //----start 插图套-题关联
                for (Question question : list1) {
                    Contact contact = new Contact();
                    contact.setExamId(eid).setType(question.getType()).setQuestionId(question.getId());
                    contactService.add(contact);
                }
                for (QuestionChoice questionChoice : list2) {
                    Contact contact = new Contact();
                    contact.setQuestionId(questionChoice.getId()).setType(questionChoice.getType()).setExamId(eid);
                    contactService.add(contact);
                }
            }
        }
        return Response.success();


    }


    @ApiOperation(value = "随机题目发布，学生抢答,redis并发", notes = "随机题目发布，学生抢答,redis并发", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pubRand")
    public Response publishRand(@RequestParam("course") String name, HttpServletRequest request) {
        Question question = questionService.listRand(name);
        QuestionChoice questionChoice = questionChoiceService.listRand(name);
        Examination examination = new Examination();
        examination.setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request))).setTime(new Date())
                .setCourse(name).setType("抢答");
        examination.setId(UUID.randomUUID().toString().replace("-", "").substring(1, 10));
        redisTemplate.opsForValue().set(examination.getId(), examination);
        examinationService.add(examination);
        if (question != null) {
            Contact contact = new Contact();
            contact.setExamId(examination.getId()).setType(question.getType()).setQuestionId(question.getId());
            contactService.add(contact);
        }
        if (questionChoice != null) {
            Contact contact2 = new Contact();
            contact2.setExamId(examination.getId()).setType(questionChoice.getType()).setQuestionId(questionChoice.getId());
            contactService.add(contact2);
        }
        return Response.success("发布成功!");

    }

    @ApiOperation(value = "学生抢答,redis并发,获得回答权，让学生输入答案", notes = "学生抢答,redis并发,获得回答权，让学生输入答案", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pubRedis")
    public Response answer(@RequestBody ExamId examId, HttpServletRequest request) {
        boolean flag = false;
        Examination examination = (Examination) redisTemplate.opsForValue().get(examId.getId());
        ReentrantLock lock = new ReentrantLock();
        if (examination.getStuId() == null) {
            lock.lock();
            try {
                examination.setStuId(JWTUtil.getUsername(JWTUtil.getToken(request)));
                redisTemplate.opsForValue().set(examination.getId(), examination);
                examinationService.updateById(examination);
                flag = true;
            } finally {
                lock.unlock();
            }
        }
        if (flag) {
            return Response.success("抢答成功！请前往题目页面进行回答！");
        } else {
            return Response.fail("题目已被占有");
        }
    }


    @ApiOperation(value = "学生回答问题后，塞入回答的答案传回", notes = "学生回答问题后，塞入回答的答案传回", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/answer")
    public Response ans(@RequestBody AnsList ansList, HttpServletRequest request) {
        AnsRecord ansRecord = new AnsRecord();
        List<Answer> list = ansList.getList();
        ansRecord.setCount(list.size()).setStuId(JWTUtil.getUsername(JWTUtil.getToken(request)))
                .setType(ansRecord.getType()).setPromulgator(ansList.getPromulgator())
                .setCourseName(ansList.getName());
        int total = list.size();
        for (Answer answer : list) {
            int right = 0;
            if (answer.getType().equals("选择")) {
                QuestionChoice questionChoice = questionChoiceService.getOne(answer.getId());
                if (questionChoice.getAnswer().equalsIgnoreCase(answer.getContent())) {
                    right++;
                }
            } else if (answer.getType().equals("判断")) {
                Question question = questionService.getOne(answer.getId());
                if (question.getAnswer().equalsIgnoreCase(answer.getContent())) {
                    right++;
                }
            }
            double result = (double) Math.round((right / total) * 1000) / 1000;
            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
            ansRecord.setResult(decimalFormat.format(result));
        }
        return ansRecordService.add(ansRecord);

    }

    @ApiOperation(value = "学生点击套题后，返回要回答的所有题目", notes = "学生点击套题后，返回要回答的所有题目", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/getExam")
    public Response getExam(@RequestParam("id") String id) {
        QuesList quesList = new QuesList();
        Examination examination = examinationService.getExam(id);
        List<Question> questions = questionService.getL(id);
        List<QuestionChoice> questionChoices = questionChoiceService.getL(id);
        quesList.setName(examination.getCourse());
        quesList.setPromulgator(examination.getPromulgator());
        quesList.setQuestionChoices(questionChoices);
        quesList.setQuestions(questions);
        return Response.success(quesList);

    }


}

