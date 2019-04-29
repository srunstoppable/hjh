package com.example.hjh.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author s r
 * @date 2019/4/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Jw {
    double latitude;
    double longitude;
    String name;
}
