package com.ytkj.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/4/12
 */
public class JsonUtil {
    public static boolean isJsonObject(String value) {
        if(StringUtils.isNotBlank(value)&&((value.contains("{")&&!value.contains("["))||(value.contains("}")&&!value.contains("]")))){
            return true;
        }
        return false;
    }

    public static boolean isJsonArrayNoJsonObject(String value) {
        if(StringUtils.isNotBlank(value)&&((value.contains("[")&&!value.contains("{"))||(value.contains("]")&&!value.contains("}")))){
            return true;
        }
        return false;
    }

    public static boolean isJsonArrayHasJsonObject(String value) {
        if(StringUtils.isNotBlank(value)&&((value.contains("[")&&value.contains("{"))||(value.contains("]")&&value.contains("}")))){
            return true;
        }
        return false;
    }
}
