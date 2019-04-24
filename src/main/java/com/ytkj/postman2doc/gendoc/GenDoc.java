package com.ytkj.postman2doc.gendoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.ytkj.postman2doc.gendoc.data.DataItem;
import com.ytkj.postman2doc.gendoc.data.DetailPolicy;
import com.ytkj.postman2doc.gendoc.data.PostmanData;
import com.ytkj.postman2doc.model.CollectionModel;
import com.ytkj.postman2doc.model.HeaderItem;
import com.ytkj.postman2doc.model.HttpItem;
import com.ytkj.util.JsonStrFormat;
import com.ytkj.util.JsonUtil;
import com.ytkj.util.ParseUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/3/1
 */
@Slf4j
public class GenDoc {
    public static void genDocByCollectionModel(CollectionModel collectionModel,RestTemplate restTemplate, HttpServletResponse response) throws IOException {
        File dataTemplate=ResourceUtils.getFile("classpath:dataTemplate.docx");
        File itemTemplate = ResourceUtils.getFile("classpath:itemTemplate.docx");
        PostmanData postmanData = new PostmanData();
        List<HttpItem> items = new ArrayList<>();
        if(collectionModel.getItem()!=null&&collectionModel.getItem().size()>0){
            collectionModel.getItem().forEach(postmanItem -> {
                if(postmanItem!=null&&postmanItem.getItem()!=null){
                    items.addAll(postmanItem.getItem());
                }else{
                    HttpItem item=new HttpItem();
                    item.setName(postmanItem.getName());
                    item.setRequest(postmanItem.getRequest());
                    item.setResponse(postmanItem.getResponse());
                    items.add(item);
                }
            });
        }

        List<DataItem>  dataItems = new ArrayList<>();
        for(HttpItem item:items){
            DataItem dataItem = new DataItem();
            dataItem.setDescription(item.getName());
            dataItem.setUrl(item.getRequest().getUrl());
            dataItem.setMethod(item.getRequest().getMethod());
            //添加header
            List<HeaderItem> headerItems = item.getRequest().getHeader();
            List<RowRenderData> headerItemRow = new ArrayList<>(headerItems.size());
            for(HeaderItem headerItem:headerItems){
                RowRenderData data = RowRenderData.build("请求头",headerItem.getKey(),headerItem.getValue());
                headerItemRow.add(data);
            }
            dataItem.getHeaderData().setReqHeaders(headerItemRow);
            //添加请求参数
            String requestParam = item.getRequest().getBody().getRaw();
            //发送http请求时的参数
            Map<String, Object> requestParamMap = new HashMap<String, Object>();
            if(StringUtils.isNotBlank(requestParam)){
                dataItem.setReqParamsExample(JsonStrFormat.format(requestParam));
                JSONObject jsonObject=null;
                try {
                    jsonObject= JSON.parseObject(requestParam);
                }catch (Exception e){
                    log.error("cast fail {}",requestParam);
                }
                if(jsonObject!=null){
                    List<RowRenderData> reqParamsRow = new ArrayList<>(jsonObject.keySet().size());
                    addReqParamsRow(reqParamsRow,jsonObject,requestParamMap);
                    dataItem.getHeaderData().setReqParams(reqParamsRow);
                }
            }else if(HttpMethod.resolve(dataItem.getMethod())==HttpMethod.GET){
                //参数为空如果为GET方式
                JSONObject jsonObject = new JSONObject();
                ParseUrlUtil.parser(dataItem.getUrl(),jsonObject);
                List<RowRenderData> reqParamsRow = new ArrayList<>(jsonObject.keySet().size());
                addReqParamsRow(reqParamsRow,jsonObject,requestParamMap);
                dataItem.getHeaderData().setReqParams(reqParamsRow);
            }
            //添加返回参数
            if(StringUtils.isNotBlank(item.getRequest().getUrl())){
                String apiURL;
                if(item.getRequest().getUrl().startsWith("http://")||item.getRequest().getUrl().startsWith("https://")){
                    apiURL= item.getRequest().getUrl();
                }else{
                    apiURL= "http://" + item.getRequest().getUrl();
                }
                HttpHeaders requestHeaders = new HttpHeaders();
                if(item.getRequest().getHeader()!=null&&item.getRequest().getHeader().size()>0){
                    item.getRequest().getHeader().forEach(headerItem -> {
                        requestHeaders.add(headerItem.getKey(),headerItem.getValue());
                    });
                }
                HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestParamMap, requestHeaders);
                HttpMethod httpMethod=getHttpMethod(item.getRequest().getMethod());
                try {
                    ResponseEntity<String> entity = restTemplate.exchange(apiURL,httpMethod,request, String.class);
                    String responseResult = entity.getBody();
                    if(responseResult!=null){
                        dataItem.setRspResultExample(JsonStrFormat.format(responseResult));
                    }
                }catch (Exception e){
                    dataItem.setRspResultExample(e.getMessage());
                }
            }
            dataItems.add(dataItem);
        }
        DocxRenderData item = new DocxRenderData(itemTemplate,dataItems);
        postmanData.setItem(item);
        Configure config = Configure.newBuilder().customPolicy("headerData", new DetailPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(dataTemplate,config).render(postmanData);
        response.setHeader("Content-disposition", "attachment; filename=" + "interface"+ ".docx");
        OutputStream out=response.getOutputStream();
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**
     * 添加请求行
     * @param reqParamsRow
     * @param jsonObject
     */
    private static void addReqParamsRow(List<RowRenderData> reqParamsRow,JSONObject jsonObject,Map<String, Object> requestParamMap){
        for(String key:jsonObject.keySet()){
            String value=jsonObject.getString(key);
            try {
                if(JsonUtil.isJsonObject(value)){
                    JSONObject jo=jsonObject.getJSONObject(key);
                    requestParamMap.put(key,jo);
                    addReqParamsRow(reqParamsRow,jo,requestParamMap);
                }else if(JsonUtil.isJsonArrayNoJsonObject(value)){
                    requestParamMap.put(key,jsonObject.getJSONArray(key));
                    RowRenderData data = RowRenderData.build("请求参数",key, jsonObject.getString(key));
                    reqParamsRow.add(data);
                }else if(JsonUtil.isJsonArrayHasJsonObject(value)){

                }else{
                    requestParamMap.put(key,jsonObject.getString(key));
                    RowRenderData data = RowRenderData.build("请求参数",key, jsonObject.getString(key));
                    reqParamsRow.add(data);
                }
            }catch (Exception e){
                log.error(value);
            }
        }
    }

    private static HttpMethod getHttpMethod(String method){
        return HttpMethod.resolve(method);
    }

}
