package com.pf.play.rule.core.model;

import java.util.Date;

public class DisPlayCustomized {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 玩法id
     *
     * @mbggenerated
     */
    private String playId;

    /**
     * 玩法名称
     *
     * @mbggenerated
     */
    private String playName;

    /**
     * 玩法备注
     *
     * @mbggenerated
     */
    private String playRemarks;

    /**
     * 定制关键字
     *
     * @mbggenerated
     */
    private String playKeyword;

    /**
     * 添加时间数据格式
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * 修改时间数据格式
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlayRemarks() {
        return playRemarks;
    }

    public void setPlayRemarks(String playRemarks) {
        this.playRemarks = playRemarks;
    }

    public String getPlayKeyword() {
        return playKeyword;
    }

    public void setPlayKeyword(String playKeyword) {
        this.playKeyword = playKeyword;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}