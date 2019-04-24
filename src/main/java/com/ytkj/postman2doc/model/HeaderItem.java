package com.ytkj.postman2doc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/3/1
 */
@Data
public class HeaderItem implements Serializable {
    private String key;

    private String value;

    private String name;

    private String description;
}
