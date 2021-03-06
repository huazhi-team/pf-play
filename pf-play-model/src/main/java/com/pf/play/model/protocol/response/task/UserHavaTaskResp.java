package com.pf.play.model.protocol.response.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/1 18:25
 * @Version 1.0
 */
public class UserHavaTaskResp {
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
     * 次产砖石
     *
     * @mbggenerated
     */
    private  Double everyNum;

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
    private Float  activeValue;


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

    public Double getEveryNum() {
        return everyNum;
    }

    public void setEveryNum(Double everyNum) {
        this.everyNum = everyNum;
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

    public Float getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Float activeValue) {
        this.activeValue = activeValue;
    }
}
