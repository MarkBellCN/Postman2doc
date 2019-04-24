package com.ytkj.postman2doc.gendoc.data;

import com.deepoove.poi.data.RowRenderData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019-03-02.
 */
@Data
public class HeaderData {

    private List<RowRenderData> reqParams ;

    private List<RowRenderData> reqHeaders ;

}
