package com.example.hjh.mapper;

import com.example.hjh.entity.Examination;
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
public interface ExaminationMapper extends BaseMapper<Examination> {
    @Select("select * from examination where isNull(stu_id) and course = #{course}")
    public List<Examination>exams(@Param("course")String course);
}
