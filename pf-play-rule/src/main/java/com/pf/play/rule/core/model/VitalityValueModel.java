package com.pf.play.rule.core.model;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/21 10:54
 * @Version 1.0
 */
public class VitalityValueModel {
    /**
     * 类型 1是全部成功， 2是有失败的
     */
    private Integer  type;
    /**
     * 成功数量
     */
    private Integer  successCount;
    /**
     * 失败数量
     */
    private Integer  failCount;
    /**
     * 成功对象
     */
    private List<Object>  successObject;
    /**
     * 失败对象
     */
    private List<Object>  failObject;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public List<Object> getSuccessObject() {
        return successObject;
    }

    public void setSuccessObject(List<Object> successObject) {
        this.successObject = successObject;
    }

    public List<Object> getFailObject() {
        return failObject;
    }

    public void setFailObject(List<Object> failObject) {
        this.failObject = failObject;
    }
}
