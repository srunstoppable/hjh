package com.example.hjh.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hjh
 * @date 2019/4/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Answer {

    private int id;
    private String content;
    private String type;
}
