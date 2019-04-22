package com.example.hjh.entity.condition;

import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author hjh
 * @date 2019/4/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConditionEntity {
    private int id;
    private String name;
    private String promulgator;
    private List<String> list;
    private String type;
    private Date date;
    private List<Question> questions;
    private List<QuestionChoice >questionChoices;

}
