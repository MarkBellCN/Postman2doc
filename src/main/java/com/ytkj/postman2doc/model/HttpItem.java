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
public class HttpItem implements Serializable {
    private String name;

    private RequestItem request;

    private List<ResponseItem> response;
}
