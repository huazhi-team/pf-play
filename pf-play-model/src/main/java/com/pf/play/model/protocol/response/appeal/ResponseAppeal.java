package com.pf.play.model.protocol.response.appeal;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/5 20:38
 * @Version 1.0
 */
public class ResponseAppeal extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331101L;

    public List<Appeal> aList;

    public Integer rowCount;

    public ResponseAppeal(){

    }
}
