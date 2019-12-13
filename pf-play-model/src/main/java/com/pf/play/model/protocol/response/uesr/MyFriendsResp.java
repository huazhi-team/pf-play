package com.pf.play.model.protocol.response.uesr;

import com.pf.play.model.protocol.response.my.InviteMy;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 13:40
 * @Version 1.0
 */
public class MyFriendsResp {
    /**
     * 直推总人数
     */
    private Integer  pushPeople;
    /**
     * 团队总人数
     */
    private Integer  teamPeople;
    /**
     * 团队总活力值
     */
    private Double  teamActive;
    /**
     * 英雄活力值
     */
    private Double  heroActive;
    /**
     * 联盟活力值
     */
    private Double  allianceActive;
    /**
     * 我的直推人数
     */
    private Integer    myInvinteCount;
    /**
     * 推荐我的详细信息
     */
    private InviteMy inviteMyList;

    /**
     * 我直推人的详细信息
     */
    private List<InviteMy> myInviteList;

    public Integer getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Integer pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }

    public Double getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Double teamActive) {
        this.teamActive = teamActive;
    }

    public Double getHeroActive() {
        return heroActive;
    }

    public void setHeroActive(Double heroActive) {
        this.heroActive = heroActive;
    }

    public Double getAllianceActive() {
        return allianceActive;
    }

    public void setAllianceActive(Double allianceActive) {
        this.allianceActive = allianceActive;
    }

    public Integer getMyInvinteCount() {
        return myInvinteCount;
    }

    public void setMyInvinteCount(Integer myInvinteCount) {
        this.myInvinteCount = myInvinteCount;
    }

    public InviteMy getInviteMyList() {
        return inviteMyList;
    }

    public void setInviteMyList(InviteMy inviteMyList) {
        this.inviteMyList = inviteMyList;
    }

    public List<InviteMy> getMyInviteList() {
        return myInviteList;
    }

    public void setMyInviteList(List<InviteMy> myInviteList) {
        this.myInviteList = myInviteList;
    }
}
