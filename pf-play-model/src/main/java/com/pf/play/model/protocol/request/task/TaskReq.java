package com.pf.play.model.protocol.request.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 13:56
 * @Version 1.0
 */
public class TaskReq {
    /***
     * 访问token
     */
    private  String   token;
    /**
     * 访问微信id
     */
    private  String   wxOpenId;
    /***
     * taskId
     */
    private  Integer  taskId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
