package com.pf.play.model.protocol.response.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/28 16:29
 * @Version 1.0
 */
public class MyUserInfoResp {
    private String nickname;
    private String memberAdd;
    private Integer darenLevel;
    private Integer empiricalLevel;
    private Integer sex;
    private String birthday;
    private String province;
    private String city;
    private Double empiricalValue;
    private Double activeValue;
    private Double masonry;
    private Integer isCertif;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public Integer getDarenLevel() {
        return darenLevel;
    }

    public void setDarenLevel(Integer darenLevel) {
        this.darenLevel = darenLevel;
    }

    public Integer getEmpiricalLevel() {
        return empiricalLevel;
    }

    public void setEmpiricalLevel(Integer empiricalLevel) {
        this.empiricalLevel = empiricalLevel;
    }

    public Double getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Double empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public Double getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Double activeValue) {
        this.activeValue = activeValue;
    }

    public Double getMasonry() {
        return masonry;
    }

    public void setMasonry(Double masonry) {
        this.masonry = masonry;
    }

    public Integer getIsCertif() {
        return isCertif;
    }

    public void setIsCertif(Integer isCertif) {
        this.isCertif = isCertif;
    }
}
