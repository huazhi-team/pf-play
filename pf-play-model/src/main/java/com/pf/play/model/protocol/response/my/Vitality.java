package com.pf.play.model.protocol.response.my;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 22:18
 * @Version 1.0
 */
public class Vitality {

    private  String  vitalityName;
    private  String pushNumber;
    private  String teamVitalitNum;
    private  String allianceVitalitNum;
    private  String  rewardNum;
    private  Integer  upgradeNum;
    private  Integer  level;
    private  String  remarks;

    public String getVitalityName() {
        return vitalityName;
    }

    public void setVitalityName(String vitalityName) {
        this.vitalityName = vitalityName;
    }

    public String getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(String pushNumber) {
        this.pushNumber = pushNumber;
    }

    public String getTeamVitalitNum() {
        return teamVitalitNum;
    }

    public void setTeamVitalitNum(String teamVitalitNum) {
        this.teamVitalitNum = teamVitalitNum;
    }

    public String getAllianceVitalitNum() {
        return allianceVitalitNum;
    }

    public void setAllianceVitalitNum(String allianceVitalitNum) {
        this.allianceVitalitNum = allianceVitalitNum;
    }

    public String getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(String rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getUpgradeNum() {
        return upgradeNum;
    }

    public void setUpgradeNum(Integer upgradeNum) {
        this.upgradeNum = upgradeNum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
