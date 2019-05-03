package com.example.hjh.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author s r
 * @date 2019/5/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AnsRecordChange {

    private Integer id;

    private String promulgator;

    private String stuId;

    private String name;

    private String type;

    private String result;

    private String courseName;

    private Integer count;

    private String time;
}
