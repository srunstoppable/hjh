package com.example.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Examination implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    @TableField("promulgator")
    private String promulgator;
    @TableField("time")
    private Date time;
    @TableField("stu_id")
    private String stuId;
    @TableField("course")
    private String course;
    @TableField("type")
    private String type;


}
