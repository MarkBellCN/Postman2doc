package com.ytkj.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2019/4/12
 */
public class JsonStrFormat {
    public static String trim(String jsonStr){
        if(StringUtils.isNotBlank(jsonStr)){
            return jsonStr.replaceAll("\\r|\\t|\n|\\\\n|\\\\r|\\\\t","");
        }
        return "";
    }

    public static String format(String jsonStr){
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int index=0;index<jsonStr.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = jsonStr.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("    ");
        }
        return levelStr.toString();
    }

}
