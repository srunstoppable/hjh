package com.example.hjh.entity.condition;

import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author s r
 * @date 2019/4/30
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Condit {
    private int id;
    private String name;
    private String promulgator;
    private List<String> list;
    private String type;
    private Date date;
    private List<Integer> questions;
    private List<Integer>questionChoices;
}
