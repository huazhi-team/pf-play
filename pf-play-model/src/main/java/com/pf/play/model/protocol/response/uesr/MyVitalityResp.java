package com.pf.play.model.protocol.response.uesr;

import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 22:37
 * @Version 1.0
 */
public class MyVitalityResp {
    /**
     * 升级直推总人数
     */
    private  Double  pushPeopleNum;
    /**
     * 升级需要团队总活力值
     */
    private  Double  teamActiveNum;
    /**
     * 升级需要联盟总活力值
     */
    private  Double  allianceActiveNum;
    /**
     * 当前直推总人数
     */
    private  Double  pushPeople;
    /**
     * 当前团队活力值
     */
    private  Double  teamActive;
    /**
     * 当前联盟总活力值
     */
    private  Double  allianceActive;

    /**
     * 当前等级
     */
    private  Integer level;
    /**
     * 当前联盟总活力值
     */
    private List<Vitality> list;

    public Double getPushPeopleNum() {
        return pushPeopleNum;
    }

    public void setPushPeopleNum(Double pushPeopleNum) {
        this.pushPeopleNum = pushPeopleNum;
    }

    public Double getTeamActiveNum() {
        return teamActiveNum;
    }

    public void setTeamActiveNum(Double teamActiveNum) {
        this.teamActiveNum = teamActiveNum;
    }

    public Double getAllianceActiveNum() {
        return allianceActiveNum;
    }

    public void setAllianceActiveNum(Double allianceActiveNum) {
        this.allianceActiveNum = allianceActiveNum;
    }

    public Double getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Double pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Double getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Double teamActive) {
        this.teamActive = teamActive;
    }

    public Double getAllianceActive() {
        return allianceActive;
    }

    public void setAllianceActive(Double allianceActive) {
        this.allianceActive = allianceActive;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Vitality> getList() {
        return list;
    }

    public void setList(List<Vitality> list) {
        this.list = list;
    }
}
