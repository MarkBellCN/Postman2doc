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
public class RequestItem implements Serializable {
    private String url;

    private String method;

    private String description;

    private List<HeaderItem> header;

    private RequestBodyItem body;
}
