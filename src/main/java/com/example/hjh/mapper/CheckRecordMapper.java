package com.example.hjh.mapper;

import com.example.hjh.entity.CheckRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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
public interface CheckRecordMapper extends BaseMapper<CheckRecord> {


    //老师查询
    @Select("select * from check_record where course in (select name from course where userid = #{id})")
    public List<CheckRecord>get(@Param("id")String id);
    //学生查询

    @Select("select * from check_record where userid = #{id}")
    public List<CheckRecord>getStuRecord(@Param("id")String id);


    @Select("select * from check_record where to_days(date) =to_days(now()) and userid = #{id} and course = #{course}")
    public  List<CheckRecord> reCheck(@Param("date") Date date,@Param("id") String id,@Param("course") String couese);
}
