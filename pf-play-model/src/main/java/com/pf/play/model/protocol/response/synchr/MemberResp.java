package com.pf.play.model.protocol.response.synchr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/10 12:12
 * @Version 1.0
 */
public class MemberResp {
    /**
     * 总砖石数
     *
     * @mbggenerated
     */
    private Float dayMasonry;

    /**
     * 达人等级
     *
     * @mbggenerated
     */
    private Integer darenLevel;

    /**
     * 经验等级数
     *
     * @mbggenerated
     */
    private Integer empiricalLevel;

    public Float getDayMasonry() {
        return dayMasonry;
    }

    public void setDayMasonry(Float dayMasonry) {
        this.dayMasonry = dayMasonry;
    }

    public Integer getDarenLevel() {
        return darenLevel;
    }

    public void setDarenLevel(Integer darenLevel) {
        this.darenLevel = darenLevel;
    }

    public Integer getEmpiricalLevel() {
        return empiricalLevel;
    }

    public void setEmpiricalLevel(Integer empiricalLevel) {
        this.empiricalLevel = empiricalLevel;
    }
}
