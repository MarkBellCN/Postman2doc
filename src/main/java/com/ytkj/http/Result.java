package com.ytkj.http;

import com.ytkj.util.CommonEnum;
import com.ytkj.util.ResultCode;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2018/4/12
 */
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result() {
    }

    public static Result success(Object data){
        return new Result(ResultCode.SUCCESSCODE,CommonEnum.SUCCESS.getValue(),data);
    }
    public static Result success(String msg){
        return new Result(ResultCode.SUCCESSCODE,msg,null);
    }

    public static Result success(){
        return new Result(ResultCode.SUCCESSCODE, CommonEnum.SUCCESS.getValue(),null);
    }

    public static Result fail(){
        return new Result(ResultCode.FAILCODE,CommonEnum.FAIL.getValue(),null);
    }
    
    public static Result fail(int code, String msg){
        return new Result(code,msg,null);
    }

    public static Result fail(String msg){
        return new Result(ResultCode.FAILCODE,msg,null);
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
