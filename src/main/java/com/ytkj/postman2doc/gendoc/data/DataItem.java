package com.ytkj.postman2doc.gendoc.data;

import com.deepoove.poi.config.Name;
import com.ytkj.postman2doc.model.HeaderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lafangyuan on 2019/3/1.
 */
@Data
public class DataItem {

    @Name("description")
    private String description;

    @Name("url")
    private String url;

    @Name("method")
    private String method;

    @Name("headerData")
    HeaderData headerData = new HeaderData();

    @Name("reqParamsExample")
    private String reqParamsExample;

    @Name("rspResultExample")
    private String rspResultExample;

}
