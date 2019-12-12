package com.pf.play.model.protocol.response.uesr;

import com.pf.play.model.protocol.response.my.Empirical;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 20:36
 * @Version 1.0
 */
public class MyEmpiricalResp {
    private  Double  empiricalValue;
    private List<Empirical>   list;

    public Double getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Double empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public List<Empirical> getList() {
        return list;
    }

    public void setList(List<Empirical> list) {
        this.list = list;
    }
}
