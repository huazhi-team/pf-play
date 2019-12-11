package com.pf.play.model.protocol.response.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/1 16:57
 * @Version 1.0
 */
public class ReceiveTaskResp {
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
     * 持有数量
     *
     * @mbggenerated
     */
    private Float holdNumber;

    /**
     * 购买所需虚拟资源  CV单位
     *
     * @mbggenerated
     */
    private Float needResource;

    /**
     * 任务周期
     *
     * @mbggenerated
     */
    private Integer taskCircleDay;

    /**
     * 任务有效期
     *
     * @mbggenerated
     */
    private Integer taskValidityDay;


    /**
     * 次产数
     *
     * @mbggenerated
     */
    private Float everyNum;

    /**
     * 总产数
     *
     * @mbggenerated
     */
    private Float totalNum;

    /**
     * 玩法等级
     *
     * @mbggenerated
     */
    private Integer taskLevel;


    /**
     * 拥有总数
     *
     * @mbggenerated
     */
    private Integer havaCount;


    /**
     * 条件1
     *
     * @mbggenerated
     */
    private String  where1;

    /**
     * 条件2
     *
     * @mbggenerated
     */
    private String  where2;

    /**
     * 奖励活跃值
     *
     * @mbggenerated
     */
    private String  activeValue;




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

    public Float getHoldNumber() {
        return holdNumber;
    }

    public void setHoldNumber(Float holdNumber) {
        this.holdNumber = holdNumber;
    }

    public Float getNeedResource() {
        return needResource;
    }

    public void setNeedResource(Float needResource) {
        this.needResource = needResource;
    }

    public Integer getTaskCircleDay() {
        return taskCircleDay;
    }

    public void setTaskCircleDay(Integer taskCircleDay) {
        this.taskCircleDay = taskCircleDay;
    }

    public Integer getTaskValidityDay() {
        return taskValidityDay;
    }

    public void setTaskValidityDay(Integer taskValidityDay) {
        this.taskValidityDay = taskValidityDay;
    }

    public Float getEveryNum() {
        return everyNum;
    }

    public void setEveryNum(Float everyNum) {
        this.everyNum = everyNum;
    }

    public Float getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Float totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(Integer taskLevel) {
        this.taskLevel = taskLevel;
    }

    public Integer getHavaCount() {
        return havaCount;
    }

    public void setHavaCount(Integer havaCount) {
        this.havaCount = havaCount;
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

    public String getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(String activeValue) {
        this.activeValue = activeValue;
    }
}
