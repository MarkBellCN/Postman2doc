package com.ytkj.postman2doc.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/3/1
 */
@Data
public class ResponseItem implements Serializable {
    private String id;

    private String name;

    private RequestItem originalRequest;

    private String status;

    private String code;

    private List<HeaderItem> header;

    private String cookie;

    private String responseTime;

    private String body;


}
