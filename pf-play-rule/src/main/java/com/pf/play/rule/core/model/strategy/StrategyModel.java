package com.pf.play.rule.core.model.strategy;

import java.io.Serializable;

/**
 * @Description 策略表：关于一些策略配置的部署的实体属性Bean
 * @Author yoko
 * @Date 2019/11/21 21:03
 * @Version 1.0
 */
public class StrategyModel implements Serializable {
    private static final long   serialVersionUID = 1233223301143L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 策略名称
     */
    private String stgName;

    /**
     * 策略类型：1表示成交量虚假数据开关，2表示交易所时间控制
     */
    private Integer stgType;

    /**
     * 策略整形值:当策略类型等于1时（此字段值1表示虚假数据处于关闭，等于2表示开启虚假数据），等于2时（此字段值1表示双休日不交易，2表示交易）
     */
    private Integer stgNumValue;

    /**
     * 策略值：字段stg_type等于1，字段stg_num_value等于2时，则根据本字段的数据乘以倍数，等于2，表示交易时间的时间段
     */
    private String stgValue;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStgName() {
        return stgName;
    }

    public void setStgName(String stgName) {
        this.stgName = stgName;
    }

    public Integer getStgType() {
        return stgType;
    }

    public void setStgType(Integer stgType) {
        this.stgType = stgType;
    }

    public Integer getStgNumValue() {
        return stgNumValue;
    }

    public void setStgNumValue(Integer stgNumValue) {
        this.stgNumValue = stgNumValue;
    }

    public String getStgValue() {
        return stgValue;
    }

    public void setStgValue(String stgValue) {
        this.stgValue = stgValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
