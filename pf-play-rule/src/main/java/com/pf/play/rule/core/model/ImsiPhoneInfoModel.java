package com.pf.play.rule.core.model;

import java.util.List;

/**
 * @author df
 * @Description:ismi与手机号对应的信息的属性Bean
 * @create 2019-05-23 11:21
 **/
public class ImsiPhoneInfoModel {

    /**
     * 自增主键ID
     */
    private long id;

    /**
     * 设备的IMEI
     */
    private String imei;

    /**
     * 设备的IMSI
     */
    private String imsi;

    /**
     * 设备的手机号
     */
    private String phone;

    /**
     * 设备的IP
     */
    private String clientIp;

    /**
     * 设备所属的省份
     */
    private String provinceName;

    /**
     * 设备所属的城市
     */
    private String cityName;

    /**
     * 运营商:0移动，1联通，2电信
     */
    private int provider;

    /**
     * 是否有效：0有效，1无效
     */
    private int yn;

    /**
     * 字符串集合
     */
    private List<String> dataList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }
}
