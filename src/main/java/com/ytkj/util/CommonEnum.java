package com.ytkj.util;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2018/4/13
 */
public enum CommonEnum {
    YES("YES","Y"),

    NO("NO","N"),

    SUCCESS("SUCCESS","success"),

    FAIL("FAIL","fail"),

    ENABLE("ENABLE","0"),

    DISABLE("DISABLE","1");

    private String name;
    private String value;
    private CommonEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
