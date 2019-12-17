package com.pf.play.model.protocol.response.order;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/25 21:57
 * @Version 1.0
 */
public class ResponseOrder  extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public Integer buyOvertime;

    public Integer sellOverTime;

    public List<Order> oList;

    public List<ConsumerOrder> coList;

    public ConsumerOrder coOrder;

    public Integer rowCount;

    public ResponseOrder(){

    }

    public Integer getBuyOvertime() {
        return buyOvertime;
    }

    public void setBuyOvertime(Integer buyOvertime) {
        this.buyOvertime = buyOvertime;
    }

    public Integer getSellOverTime() {
        return sellOverTime;
    }

    public void setSellOverTime(Integer sellOverTime) {
        this.sellOverTime = sellOverTime;
    }

    public List<Order> getoList() {
        return oList;
    }

    public void setoList(List<Order> oList) {
        this.oList = oList;
    }

    public List<ConsumerOrder> getCoList() {
        return coList;
    }

    public void setCoList(List<ConsumerOrder> coList) {
        this.coList = coList;
    }

    public ConsumerOrder getCoOrder() {
        return coOrder;
    }

    public void setCoOrder(ConsumerOrder coOrder) {
        this.coOrder = coOrder;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
