package com.pf.play.model.protocol.response.my;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 20:38
 * @Version 1.0
 */
public class Empirical {
    /**
     * 等级名称
     */
    private String empiricalName;
    /**
     * 要求
     */
    private String remarks;
    /**
     * 可交易数
     */
    private String transactionValue;
    /**
     * 交易手续费
     */
    private String transactionFee;

    public String getEmpiricalName() {
        return empiricalName;
    }

    public void setEmpiricalName(String empiricalName) {
        this.empiricalName = empiricalName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }
}
