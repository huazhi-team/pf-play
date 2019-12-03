package com.pf.play.model.protocol.response.consumer;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/13 21:15
 * @Version 1.0
 */
public class ResponseConsumer extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233223331149L;

    public String fullName;
    public String idCard;
    public Integer fixedType;
    public String fixedNum;

    public Integer empiricalLevel;
    public String ratio;

    public ResponseConsumer(){

    }

}
