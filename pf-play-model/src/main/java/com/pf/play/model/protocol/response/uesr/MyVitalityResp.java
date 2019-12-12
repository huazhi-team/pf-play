package com.pf.play.model.protocol.response.uesr;

import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 22:37
 * @Version 1.0
 */
public class MyVitalityResp {
    /**
     * 活跃值
     */
    private  Double  vitalityValue;
    private  Double  needVitalityValue;
    private  Integer level;
    private List<Vitality> list;

    public Double getVitalityValue() {
        return vitalityValue;
    }

    public void setVitalityValue(Double vitalityValue) {
        this.vitalityValue = vitalityValue;
    }

    public List<Vitality> getList() {
        return list;
    }

    public void setList(List<Vitality> list) {
        this.list = list;
    }

    public Double getNeedVitalityValue() {
        return needVitalityValue;
    }

    public void setNeedVitalityValue(Double needVitalityValue) {
        this.needVitalityValue = needVitalityValue;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
