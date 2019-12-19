package com.pf.play.model.protocol.response.alipay;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/19 20:57
 * @Version 1.0
 */
public class ResponseAlipay extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331101L;

    public String aliOrder;


    public ResponseAlipay(){

    }

    public String getAliOrder() {
        return aliOrder;
    }

    public void setAliOrder(String aliOrder) {
        this.aliOrder = aliOrder;
    }
}
