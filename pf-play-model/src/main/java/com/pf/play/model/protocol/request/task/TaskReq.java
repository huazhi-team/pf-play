package com.pf.play.model.protocol.request.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 13:56
 * @Version 1.0
 */
public class TaskReq {
    /****
     * 用户id
     */
    private  Integer   memberId;

    /**
     * 任务类型 1 、我的任务  2、领取任务 3、历史任务
     */
    private  Integer   taskType;

    /***
     * 访问token
     */
    private  String   token;


    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
