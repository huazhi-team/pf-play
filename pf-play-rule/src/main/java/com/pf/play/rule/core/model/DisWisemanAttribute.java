package com.pf.play.rule.core.model;

import java.util.Date;

public class DisWisemanAttribute {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 达人id
     *
     * @mbggenerated
     */
    private Integer wisemanId;

    /**
     * 属性类型: 1、条件 2、奖励 3、消耗
     *
     * @mbggenerated
     */
    private Integer attributeType;

    /**
     * 字段值1
     *
     * @mbggenerated
     */
    private String key1;

    /**
     * 字段值2
     *
     * @mbggenerated
     */
    private String key2;

    /**
     * 字段值3
     *
     * @mbggenerated
     */
    private String key3;

    /**
     * 字段值4
     *
     * @mbggenerated
     */
    private String key4;

    /**
     * 字段值5
     *
     * @mbggenerated
     */
    private String key5;

    /**
     * 字段值6
     *
     * @mbggenerated
     */
    private String key6;

    /**
     * 字段值7
     *
     * @mbggenerated
     */
    private String key7;

    /**
     * 字段值8
     *
     * @mbggenerated
     */
    private String key8;

    /**
     * 字段值9
     *
     * @mbggenerated
     */
    private String key9;

    /**
     * 字段值10
     *
     * @mbggenerated
     */
    private String key10;

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
    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWisemanId() {
        return wisemanId;
    }

    public void setWisemanId(Integer wisemanId) {
        this.wisemanId = wisemanId;
    }

    public Integer getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(Integer attributeType) {
        this.attributeType = attributeType;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public String getKey6() {
        return key6;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }

    public String getKey7() {
        return key7;
    }

    public void setKey7(String key7) {
        this.key7 = key7;
    }

    public String getKey8() {
        return key8;
    }

    public void setKey8(String key8) {
        this.key8 = key8;
    }

    public String getKey9() {
        return key9;
    }

    public void setKey9(String key9) {
        this.key9 = key9;
    }

    public String getKey10() {
        return key10;
    }

    public void setKey10(String key10) {
        this.key10 = key10;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}