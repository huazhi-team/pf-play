package com.pf.play.rule.core.model.Enum;

/**
 * @Description 策略的枚举类
 * @Author yoko
 * @Date 2019/11/21 21:53
 * @Version 1.0
 */
public enum StrategyEnum {

    TRADING_VOLUME(1, "表示成交量虚假数据开关"),
    TRANSACTION_TIME_SWITCH(2, "交易所时间控制"),

    ;

    /**
     * 策略类型：1表示成交量虚假数据开关，2表示交易所时间控制
     */
    private Integer stgType;
    /**
     * 策略具体描述
     */
    private String stgDesc;

    private StrategyEnum(Integer   stgType , String stgDesc){
        this.stgType = stgType ;
        this.stgDesc = stgDesc;
    }

    public Integer getStgType() {
        return stgType;
    }

    public void setStgType(Integer stgType) {
        this.stgType = stgType;
    }

    public String getStgDesc() {
        return stgDesc;
    }

    public void setStgDesc(String stgDesc) {
        this.stgDesc = stgDesc;
    }
}
