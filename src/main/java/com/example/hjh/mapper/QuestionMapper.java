package com.example.hjh.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Question;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjh
 * @since 2019-04-07
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select * from question where name in (select name from course where userid = #{id})")
    public List<Question> courses(Page<Question> page,@Param("id") String id);

    @Select("select count(*) from question where name in (select name from course where userid = #{id})")
    public int selectTotal();

    @Select("select * from question where id in selet question_id from contact where exam_id = #{id} and type ='判断'")
    public List<Question> ques(@Param("id")String id);
}
