package com.ytkj.postman2doc.controller;

import com.alibaba.fastjson.JSONObject;
import com.ytkj.postman2doc.gendoc.GenDoc;
import com.ytkj.postman2doc.model.CollectionModel;
import com.ytkj.util.JsonStrFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/3/1
 */
@Slf4j
@RestController
public class Postman2docController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response){

        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
            InputStream in=file.getInputStream();
            String jsonStr = JsonStrFormat.trim(new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining(System.lineSeparator())));
            CollectionModel collectionModel= JSONObject.parseObject(jsonStr, CollectionModel.class);
            GenDoc.genDocByCollectionModel(collectionModel,restTemplate,response);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }
}
