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
public class PostmanItem implements Serializable {

    private String name;

    private String description;

    private List<HttpItem> item;

    private RequestItem request;

    private List<ResponseItem> response;
}
