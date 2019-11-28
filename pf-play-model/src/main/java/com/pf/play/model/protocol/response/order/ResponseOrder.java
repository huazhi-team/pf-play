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

    public Integer overtime;

    public List<Order> oList;

    public List<ConsumerOrder> coList;

    public ResponseOrder(){

    }


}
