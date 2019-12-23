package com.pf.play.model.protocol.response.my;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 15:46
 * @Version 1.0
 */
public class InviteMy {
    /**
     * 昵称
     */
    private  String  nickname;

    /**
     * 昵称
     */
    private  String  memberAdd;
    /**
     * 团队总人数
     */
    private  Integer  teamPeople;
    /**
     * 经验等级
     */
    private  Integer  empiricalLevel;
    /**
     * 达人等级
     */
    private  Integer  darenLevel;
    /**
     *上次登录时间
     */
    private  String   updateTime;
    /**
     *注册时间
     */
    private  String   createTime;

    /**
     * 电话号码
     */
    private  String   phone;

    /**
     * 是否实名制
     */
    private  Integer  isCertification;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }

    public Integer getEmpiricalLevel() {
        return empiricalLevel;
    }

    public void setEmpiricalLevel(Integer empiricalLevel) {
        this.empiricalLevel = empiricalLevel;
    }

    public Integer getDarenLevel() {
        return darenLevel;
    }

    public void setDarenLevel(Integer darenLevel) {
        this.darenLevel = darenLevel;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Integer isCertification) {
        this.isCertification = isCertification;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }
}
