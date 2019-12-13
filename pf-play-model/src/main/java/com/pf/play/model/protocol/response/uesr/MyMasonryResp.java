package com.pf.play.model.protocol.response.uesr;

import java.util.Date;
import java.util.List;

/**
 * @Description 我的资产
 * @Author long
 * @Date 2019/12/2 21:47
 * @Version 1.0
 */
public class MyMasonryResp {

    /**
     * 余额
     */
    private Double  balance ;
    /**
     * 收入
     */
    private Double  inMasonry ;
    /**
     * 支出
     */
    private Double  outMasonry ;

    private List<MasonryList>  masonrylist;


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInMasonry() {
        return inMasonry;
    }

    public void setInMasonry(Double inMasonry) {
        this.inMasonry = inMasonry;
    }

    public Double getOutMasonry() {
        return outMasonry;
    }

    public void setOutMasonry(Double outMasonry) {
        this.outMasonry = outMasonry;
    }


    public List<MasonryList> getMasonrylist() {
        return masonrylist;
    }

    public void setMasonrylist(List<MasonryList> masonrylist) {
        this.masonrylist = masonrylist;
    }
}
