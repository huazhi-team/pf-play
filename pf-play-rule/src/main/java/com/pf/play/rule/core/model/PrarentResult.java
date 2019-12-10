package com.pf.play.rule.core.model;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/10 10:54
 * @Version 1.0
 */
public class PrarentResult {
    /**
     * 返回状态码
     */
    private String errcode;
    /**
     * 返回结果
     */
    private String message;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
