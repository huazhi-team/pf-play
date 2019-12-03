package com.pf.play.model.protocol.response.trade;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/27 17:35
 * @Version 1.0
 */
public class ResponseTrade extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public String buyTradeNum;

    public String sucTradeNum;

    public ResponseTrade(){
    }

    public String getBuyTradeNum() {
        return buyTradeNum;
    }

    public void setBuyTradeNum(String buyTradeNum) {
        this.buyTradeNum = buyTradeNum;
    }

    public String getSucTradeNum() {
        return sucTradeNum;
    }

    public void setSucTradeNum(String sucTradeNum) {
        this.sucTradeNum = sucTradeNum;
    }
}
