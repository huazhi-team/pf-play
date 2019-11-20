package com.pf.play.rule.core.model;

/**
 * @author df
 * @Description:渠道每日金额分配
 * @create 2019-05-22 9:50
 **/
public class ChannelMoneyDayModel {
    /**
     * 自增主键ID
     */
    private long id;

    /**
     * 渠道号ID：对应表tb_channel_info的主键ID
     */
    private long channelId;

    /**
     * 代码ID:对应表tb_code_info的主键ID
     */
    private long codeId;

    /**
     * 金额日期：存的日期格式20160530
     */
    private int curday;

    /**
     * 金额
     */
    private String money;

    /**
     * 运行计算状态：：0初始化，1计算成功/计算完成
     */
    private int runStatus;

    /**
     * 是否有效：0初始化，1失效/删除
     */
    private int yn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getCodeId() {
        return codeId;
    }

    public void setCodeId(long codeId) {
        this.codeId = codeId;
    }

    public int getCurday() {
        return curday;
    }

    public void setCurday(int curday) {
        this.curday = curday;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }
}
