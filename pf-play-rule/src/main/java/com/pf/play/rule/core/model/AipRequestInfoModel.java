package com.pf.play.rule.core.model;

/**
 * @author df
 * @Description:请求纪录的实体属性Bean
 * @create 2019-05-27 17:01
 **/
public class AipRequestInfoModel {

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
     * 用户imei
     */
    private String imei;

    /**
     * 用户imsi
     */
    private String imsi;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户ip
     */
    private String clientIp;

    /**
     * 用户省份名字
     */
    private String provinceName;

    /**
     * 用户城市名称
     */
    private String cityName;

    /**
     * 运营商:0移动，1联通，2电信
     */
    private int provider;

    /**
     * 请求返回结果是否成功:0表示成功，非零表示失败
     */
    private int sucStatus;

    /**
     * 是否完成请求：0未完成，1完成
     */
    private int isOk;

    /**
     * 值以work形式进行计算拆分,以后数据多起来则这些字段值填充由worker来跑数据：0初始化，1计算完毕
     */
    private int workType;

    /**
     * 运行计算次数
     */
    private int runNum;

    /**
     * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private int runStatus;

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public int getSucStatus() {
        return sucStatus;
    }

    public void setSucStatus(int sucStatus) {
        this.sucStatus = sucStatus;
    }

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public int getRunNum() {
        return runNum;
    }

    public void setRunNum(int runNum) {
        this.runNum = runNum;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
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
}
