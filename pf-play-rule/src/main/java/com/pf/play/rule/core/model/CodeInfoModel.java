package com.pf.play.rule.core.model;

/**
 * @author df
 * @Description:代码信息的属性Bean
 * @create 2019-05-21 17:57
 **/
public class CodeInfoModel {
    /**
     * 自增主键ID
     */
    private long id;

    /**
     * 归属sp
     */
    private long spId;

    /**
     * 代码id
     */
    private int spcodeid;

    /**
     * 代码类型：sp增值的真实业务类型
     */
    private int typeid;

    /**
     * 代码名称
     */
    private String codeName;

    /**
     * 代码价格(元)
     */
    private String price;

    /**
     *运营商:0移动，1联通，2电信
     */
    private int provider;

    /**
     * 请求接口
     */
    private String intfaceAds;

    /**
     * 同步的接口地址
     */
    private String synchroAds;

    /**
     *代码请求成功率:100以内的数字
     */
    private int requestSucRate;

    /**
     * 代码结果成功率:100以为的数字
     */
    private int resultSucRate;

    /**
     * 开通省份:以逗号分隔
     */
    private String province;

    /**
     * 结算率：100以内的数字
     */
    private int settlementRate;

    /**
     * 是否有效：0初始化，1失效/删除
     */
    private int yn;

    /**
     * 模拟渠道的透传参数
     */
    private String clExtData;

    /**
     * sp增值透传
     */
    private String spExtData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public int getSpcodeid() {
        return spcodeid;
    }

    public void setSpcodeid(int spcodeid) {
        this.spcodeid = spcodeid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public int getRequestSucRate() {
        return requestSucRate;
    }

    public void setRequestSucRate(int requestSucRate) {
        this.requestSucRate = requestSucRate;
    }

    public int getResultSucRate() {
        return resultSucRate;
    }

    public void setResultSucRate(int resultSucRate) {
        this.resultSucRate = resultSucRate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSettlementRate() {
        return settlementRate;
    }

    public void setSettlementRate(int settlementRate) {
        this.settlementRate = settlementRate;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public String getIntfaceAds() {
        return intfaceAds;
    }

    public void setIntfaceAds(String intfaceAds) {
        this.intfaceAds = intfaceAds;
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

    public String getSynchroAds() {
        return synchroAds;
    }

    public void setSynchroAds(String synchroAds) {
        this.synchroAds = synchroAds;
    }
}
