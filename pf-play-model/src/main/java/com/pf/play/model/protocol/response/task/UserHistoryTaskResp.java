package com.pf.play.model.protocol.response.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/2 14:40
 * @Version 1.0
 */
public class UserHistoryTaskResp {
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
     * 任务等级
     *
     * @mbggenerated
     */
    private Integer taskLevel;

    /**
     * 到期时间
     *
     * @mbggenerated
     */
    private String endTimeStr;


    /**
     * 总产量
     *
     * @mbggenerated
     */
    private  Double totalNum;

    /**
     * 已经获取砖石
     *
     * @mbggenerated
     */
    private  Double alreadyNum;

    /**
     * 点赞
     *
     * @mbggenerated
     */
    private String  where1;

    /**
     * 查看商品
     *
     * @mbggenerated
     */
    private String  where2;

    /**
     * 剩余次数
     *
     * @mbggenerated
     */
    private Double surplusNum;

    /**
     * 活跃值
     *
     * @mbggenerated
     */
    private Double  activeValue;

    private Integer  currentState;

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

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Double totalNum) {
        this.totalNum = totalNum;
    }

    public Double getAlreadyNum() {
        return alreadyNum;
    }

    public void setAlreadyNum(Double alreadyNum) {
        this.alreadyNum = alreadyNum;
    }

    public String getWhere1() {
        return where1;
    }

    public void setWhere1(String where1) {
        this.where1 = where1;
    }

    public String getWhere2() {
        return where2;
    }

    public void setWhere2(String where2) {
        this.where2 = where2;
    }

    public Double getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Double surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Double getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Double activeValue) {
        this.activeValue = activeValue;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }
}
