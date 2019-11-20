package com.pf.play.rule.core.model;

/**
 * @author df
 * @Description:结果同步的实体属性Bean
 * @create 2019-05-29 14:42
 **/
public class AipResultInfoModel {
    /**
     * 自增主键ID
     */
    private long id;

    /**
     * 渠道透传参数(请求方的透传)
     */
    private String clExtData;

    /**
     * sp增值透传
     */
    private String spExtData;

    /**
     * 请求的渠道ID：对应表tb_channel_info的主键ID
     */
    private long channelId;

    /**
     * 请求的代码的ID:对应表tb_code_info的主键ID
     */
    private long codeId;

    /**
     * 代码类型：sp增值的真实业务类型
     */
    private int typeid;

    /**
     * 请求的spID:对应表tb_sp_info的主键ID
     */
    private long spId;

    /**
     * 代码价格(分)
     */
    private String price;

    /**
     * 结算率：100以内的数字
     */
    private int settlementRate;

    /**
     * 创建日期：存的日期格式20160530
     */
    private int curday;

    /**
     * 创建所属小时：24小时制
     */
    private int curhour;

    /**
     * 创建所属分钟：60分钟制
     */
    private int curminute;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     *更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0初始化，1失效/删除
     */
    private int yn;

    /**
     * 下标位-表
     */
    private String suffix;

    /**
     * 金额
     */
    private String money;

    /**
     * 增值传过来的渠道的透传参数值
     */
    private String customParam;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClExtData() {
        return clExtData;
    }

    public void setClExtData(String clExtData) {
        this.clExtData = clExtData;
    }

    public String getSpExtData() {
        return spExtData;
    }

    public void setSpExtData(String spExtData) {
        this.spExtData = spExtData;
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

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCurday() {
        return curday;
    }

    public void setCurday(int curday) {
        this.curday = curday;
    }

    public int getCurhour() {
        return curhour;
    }

    public void setCurhour(int curhour) {
        this.curhour = curhour;
    }

    public int getCurminute() {
        return curminute;
    }

    public void setCurminute(int curminute) {
        this.curminute = curminute;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getSettlementRate() {
        return settlementRate;
    }

    public void setSettlementRate(int settlementRate) {
        this.settlementRate = settlementRate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCustomParam() {
        return customParam;
    }

    public void setCustomParam(String customParam) {
        this.customParam = customParam;
    }
}
