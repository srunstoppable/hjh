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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    @Autowired
    private CourseService courseService;


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
    public Response publish(@RequestBody Condit condit, HttpServletRequest request) {

        List<String> list = condit.getList();
        List<Integer> list1 = condit.getQuestions();
        List<Question> lq = new ArrayList<>();
        if (list1 != null) {
            for (int i = 0; i < list1.size(); i++) {
                lq.add(questionService.getOne(list1.get(i)));
            }
        }
        List<QuestionChoice> lqc = new ArrayList<>();
        List<Integer> list2 =condit.getQuestionChoices();
        if (list2   != null) {
            for (int i = 0; i < list2.size(); i++) {
                lqc.add(questionChoiceService.getOne(list2.get(i)));
            }
        }
        if (list != null) {
            for (String user : list) {
                Examination examination = new Examination();
                String eid = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
                examination.setId(eid).setStuId(user).setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request)))
                        .setTime(new Date()).setCourse(condit.getName()).setType("指定");
                examinationService.add(examination);
                //-----end  插入套题记录

                //----start 插图套-题关联
                if (list1.size() != 0) {
                    for (Question question : lq) {
                        Contact contact = new Contact();
                        contact.setExamId(eid).setType(question.getType()).setQuestionId(question.getId());
                        contactService.add(contact);
                    }
                }
                if (list2.size() != 0) {
                    for (QuestionChoice questionChoice : lqc) {
                        Contact contact = new Contact();
                        contact.setQuestionId(questionChoice.getId()).setType(questionChoice.getType()).setExamId(eid);
                        contactService.add(contact);
                    }
                }
            }
        }
        return Response.success("发布成功!");
    }

    @ApiOperation(value = "指定题目发布，随机学生", notes = "指定题目发布，随机学生", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pubT")
    public Response publishT(@RequestBody Condit condit, HttpServletRequest request) {
        List<Userinfo> list = userinfoService.stuRand(condit.getName());
        List<Integer> list1 = condit.getQuestions();
        List<Question> lq = new ArrayList<>();
        if (list1 !=null) {
            for (int i = 0; i < list1.size(); i++) {
                lq.add(questionService.getOne(list1.get(i)));
            }
        }
        List<Integer> list2 = condit.getQuestionChoices();
        List<QuestionChoice> lqc = new ArrayList<>();
        if (list2 != null) {
            for (int i = 0; i < list2.size(); i++) {
                lqc.add(questionChoiceService.getOne(list2.get(i)));
            }
        }
        if (list != null) {
            for (Userinfo userinfo : list) {
                Examination examination = new Examination();
                String eid = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
                examination.setId(eid).setStuId(userinfo.getId()).setPromulgator(JWTUtil.getUsername(JWTUtil.getToken(request)))
                        .setTime(new Date()).setCourse(condit.getName()).setType("随机");
                examinationService.add(examination);
                //-----end  插入套题记录
                //----start 插图套-题关联
                if(lq.size()!=0) {
                    for (Question question : lq) {
                        Contact contact = new Contact();
                        contact.setExamId(eid).setType(question.getType()).setQuestionId(question.getId());
                        contactService.add(contact);
                    }

                }
                if(lqc.size()!=0) {
                    for (QuestionChoice questionChoice : lqc) {
                        Contact contact = new Contact();
                        contact.setQuestionId(questionChoice.getId()).setType(questionChoice.getType()).setExamId(eid);
                        contactService.add(contact);
                    }
                }
            }
        }
        return Response.success(list);


    }


    @ApiOperation(value = "随机题目发布，学生抢答,redis并发", notes = "随机题目发布，学生抢答,redis并发", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @PostMapping("/pubRand")
    public Response publishRand(@RequestBody CourseName courseName, HttpServletRequest request) {
        String name = courseName.getName();
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
        Course course =courseService.course(ansList.getName());
        AnsRecord ansRecord = new AnsRecord();
        ansRecord.setType(ansList.getType());
        ansRecord.setTime(new Date());
        List<Answer> list = ansList.getList();
        ansRecord.setCount(list.size()).setStuId(JWTUtil.getUsername(JWTUtil.getToken(request)))
                .setType(ansRecord.getType()).setPromulgator(course.getUserid())
                .setCourseName(ansList.getName());
        int total = list.size();
        int right = 0;
        if(list!=null) {
            for (Answer answer : list) {
                if (answer.getType().equals("choice")) {
                    QuestionChoice questionChoice = questionChoiceService.getOne(answer.getId());
                    if (questionChoice.getAnswer().equalsIgnoreCase(answer.getContent())) {
                        right++;
                    }
                } else if (answer.getType().equals("judge")) {
                    Question question = questionService.getOne(answer.getId());
                    if (question.getAnswer().equalsIgnoreCase(answer.getContent())) {
                        right++;
                    }
                }
            }
        }
        ansRecord.setResult(accuracy(right, total, 1));

        if(contactService.detele(ansList.getId())) {
            examinationService.deleteById(ansList.getId());
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

    @ApiOperation(value = "老师选择题目构成套题，返回题目列表", notes = "老师选择题目构成套题，返回题目列表", response = Response.class)
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, paramType = "header")
    @GetMapping("/getQ")
    public Response getQue(@RequestParam("name") String name) {
        ConditionEntity conditionEntity = new ConditionEntity();
        conditionEntity.setQuestions(questionService.lists(name));
        conditionEntity.setQuestionChoices(questionChoiceService.lists(name));
        return Response.success(conditionEntity);

    }

    public static String accuracy(double num, double total, int scale){
        DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num)+"%";
    }
}

