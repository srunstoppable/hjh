package com.example.hjh.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.CourseStu;
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
public interface CourseStuMapper extends BaseMapper<CourseStu> {

    @Select("select * from course_stu where course in (select name from course where userid = #{id})")
    public List<CourseStu>getAll(Page<CourseStu>page,@Param("id") String id);

    @Select("select count(*) from course_stu where course in (select name from course where userid = #{id})")
    public int count(@Param("id")String id);
}
