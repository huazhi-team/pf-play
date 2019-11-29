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
    private  Float  vitalityValue;
    private List<Vitality> list;

    public Float getVitalityValue() {
        return vitalityValue;
    }

    public void setVitalityValue(Float vitalityValue) {
        this.vitalityValue = vitalityValue;
    }

    public List<Vitality> getList() {
        return list;
    }

    public void setList(List<Vitality> list) {
        this.list = list;
    }
}
