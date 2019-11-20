package com.pf.play.rule.core.model.Enum;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/14 14:53
 * @Version 1.0
 */
public enum  MasonryTypeEnum {
//    TYPE1("完成任务"),
//    TYPE2("活力值奖励1111"),
//    TYPE3("赠送给Ta"),
//    TYPE4("Ta人赠送"),
//    TYPE5("转出手续费"),
//    TYPE6("转入手续费"),
//    TYPE7("交易所转入"),
//    TYPE8("转出砖石"),
    TYPE9("购买任务消耗");

    private String   value;
    private MasonryTypeEnum(String   value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
