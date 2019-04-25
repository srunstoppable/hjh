package com.example.hjh.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
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
 * @since 2019-04-13
 */
@Mapper
public interface QuestionChoiceMapper extends BaseMapper<QuestionChoice> {


    @Select("select * from question_choice where name in (select name from course where userid = #{id})")
    public List<QuestionChoice> courses(Page<QuestionChoice> page, @Param("id") String id);

    @Select("select count(*) from question_choice where name in (select name from course where userid = #{id})")
    public int selectTotal();

    @Select("select * from question_choice where id in (select question_id from contact where exam_id = #{id} and type ='choice')")
    public List<QuestionChoice> choice(@Param("id")String id);

}
