package com.example.hjh.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author s r
 * @date 2019/5/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MulFile {
    private MultipartFile file;
    private String name;
}
