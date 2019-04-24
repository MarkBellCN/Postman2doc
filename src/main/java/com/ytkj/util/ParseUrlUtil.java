package com.ytkj.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/4/12
 */
public class ParseUrlUtil {
    public static void parser(String url, JSONObject strUrlParas){
        strUrlParas.clear();
        String strUrl = "";
        String strUrlParams = "";


        if(url.contains("?")){
            String[] strUrlPatten = url.split("\\?");
            strUrl = strUrlPatten[0];
            strUrlParams = strUrlPatten[1];

        }else{
            strUrl = url;
            strUrlParams = url;
        }

        String[] params = null;

        if(strUrlParams.contains("&")){
            params = strUrlParams.split("&");
        }else{
            params = new String[] {strUrlParams};
        }

        for(String p:params){
            if(p.contains("=")) {
                String[] param = p.split("=");
                if(param.length==1){
                    strUrlParas.put(param[0],"");
                }else{

                    String key = param[0];
                    String value = param[1];

                    strUrlParas.put(key, value);
                }
            }else {

            }
        }

    }
}
