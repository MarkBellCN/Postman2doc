package com.ytkj.postman2doc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/3/1
 */
@Data
public class RequestBodyItem implements Serializable {
    private String mode;

    private String raw;

}
