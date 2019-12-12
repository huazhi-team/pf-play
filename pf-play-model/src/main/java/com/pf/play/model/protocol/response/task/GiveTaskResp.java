package com.pf.play.model.protocol.response.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/2 17:59
 * @Version 1.0
 */
public class GiveTaskResp {

    /**
     * 等级
     *
     * @mbggenerated
     */
    private Integer level;


    /**
     * 任务id
     *
     * @mbggenerated
     */
    private Integer taskId;

    /**
     * 任务名称
     *
     * @mbggenerated
     */
    private String taskName;

    /**
     * 要求任务等级
     *
     * @mbggenerated
     */
    private Integer taskLevel;


    /**
     * 是否显示; 1显示 2不显示
     *
     * @mbggenerated
     */
    private  Integer isDisplay;
    /**
     * 是否能领取; 1 已领取 2、可以领取 3、不能领取
     *
     * @mbggenerated
     */
    private  Integer isReceive;

    /**
     * 显示要求：显示给列表信息的要求
     *
     * @mbggenerated
     */
    private String remarks;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(Integer taskLevel) {
        this.taskLevel = taskLevel;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
