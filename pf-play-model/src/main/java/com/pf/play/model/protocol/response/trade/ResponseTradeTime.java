package com.pf.play.model.protocol.response.trade;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/27 19:27
 * @Version 1.0
 */
public class ResponseTradeTime extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331142L;

    public Integer isTrade;
    public String tradeTime;

    public ResponseTradeTime(){

    }


    public Integer getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(Integer isTrade) {
        this.isTrade = isTrade;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
