package com.pf.play.common.utils.model;

import java.io.Serializable;

/**
 * @author df
 * @Description:ip号段、地域的实体Bean
 * @create 2019-02-13 20:54
 **/
public class RegionModel implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1115110409167768411L;

    private long id;
    /**
     * ip值，算好的值
     */
    private long ipValue;

    /**
     * ip地址
     */
    private String ip;

    private long startIp;

    private long endIp;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商
     */
    private String isp;

    public RegionModel(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIpValue() {
        return ipValue;
    }

    public void setIpValue(long ipValue) {
        this.ipValue = ipValue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getStartIp() {
        return startIp;
    }

    public void setStartIp(long startIp) {
        this.startIp = startIp;
    }

    public long getEndIp() {
        return endIp;
    }

    public void setEndIp(long endIp) {
        this.endIp = endIp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
