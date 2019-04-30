package com.example.hjh.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author s r
 * @date 2019/4/29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamChange {
    private String id;
    private String promulgator;
    private String time;
    private String stuId;
    private String course;
    private String type;
}
