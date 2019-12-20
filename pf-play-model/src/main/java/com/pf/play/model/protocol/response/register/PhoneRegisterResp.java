package com.pf.play.model.protocol.response.register;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/20 14:05
 * @Version 1.0
 */
public class PhoneRegisterResp {
    /**
     * 状态 1 是成功  2 是失败
     */
    private  Integer  state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
