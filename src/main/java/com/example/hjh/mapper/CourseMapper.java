package com.example.hjh.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Course;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjh
 * @since 2019-03-30
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select * from course")
    public List<Course>courses(Page<Course>page);

    @Select("select count(*) from course")
    public int selectTotal();


}
