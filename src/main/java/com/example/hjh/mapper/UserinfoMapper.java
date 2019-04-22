package com.example.hjh.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.hjh.entity.Userinfo;
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
 * @since 2019-03-30
 */
@Mapper
public interface UserinfoMapper extends BaseMapper<Userinfo> {

    @Select("select * from userinfo where iden = '教师'or iden = '学生'")
    public List<Userinfo>users(Page<Userinfo> page);

    @Select("select count(*) from userinfo where iden = '教师' or iden = '学生'")
    public  int selectTotal();

    @Select("select * from userinfo where id in (select userid from course_stu where course = #{name}) and iden = '学生'")
    public List<Userinfo> student(@Param("name")String name);



}
