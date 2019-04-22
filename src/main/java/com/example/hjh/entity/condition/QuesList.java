package com.example.hjh.entity.condition;

import com.example.hjh.entity.Question;
import com.example.hjh.entity.QuestionChoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author s r
 * @date 2019/4/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuesList {
    private String promulgator;
    private List<Question> questions;
    private List<QuestionChoice>questionChoices;
    private String name;
}
