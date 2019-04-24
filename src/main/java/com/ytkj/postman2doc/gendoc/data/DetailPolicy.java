package com.ytkj.postman2doc.gendoc.data;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-03-03.
 */
public class DetailPolicy extends DynamicTableRenderPolicy {

    int startReqHeaderRow = 3;
    int startReqParamsRow;
    HeaderData headerData = null;
    XWPFTable table = null;

    @Override
    public void render(XWPFTable tb, Object o) {
        HeaderData data = (HeaderData) o;
        if(data==null){
            return;
        }
        this.headerData = data;
        this.table = tb;
        List<RowRenderData> list = data.getReqHeaders();
        table.removeRow(startReqHeaderRow);
        for(int i=0;i<list.size();i++){
            XWPFTableRow row  = table.insertNewTableRow(startReqHeaderRow);
            for(int j=0;j<3;j++){
                row.createCell();
            }
            MiniTableRenderPolicy.renderRow(table,startReqHeaderRow,list.get(i));
        }
        TableTools.mergeCellsVertically(table,0,startReqHeaderRow,startReqHeaderRow+list.size()-1);
        renderReqParams();

    }

    private void renderReqParams(){
        List<RowRenderData> list =  headerData.getReqParams();
        startReqParamsRow = startReqHeaderRow+headerData.getReqHeaders().size()+2;
        table.removeRow(startReqParamsRow);
        if(list!=null){
            for(int i=0;i<list.size();i++){
                XWPFTableRow row  = table.insertNewTableRow(startReqParamsRow);
                for(int j=0;j<3;j++){
                    row.createCell();
                }
                MiniTableRenderPolicy.renderRow(table,startReqParamsRow,list.get(i));
            }
            TableTools.mergeCellsVertically(table, 0, startReqParamsRow, startReqParamsRow + list.size() - 1);
        }

    }
}
