package com.example.hjh.entity.condition;

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
public class AnsList {
    private String type;
    private String name;
    private String promulgator;
    private List<Answer> list;
    private String id;
}
