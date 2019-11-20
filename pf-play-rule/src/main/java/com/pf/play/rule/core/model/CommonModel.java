package com.pf.play.rule.core.model;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/14 10:29
 * @Version 1.0
 */
public class CommonModel {
    private  String  message;
    private  Integer code;
    private  Object  object;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
