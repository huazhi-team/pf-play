package com.pf.play.rule.core.model;

import java.util.Date;

public class SysTypeDictionary {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 类型类型值
     *
     * @mbggenerated
     */
    private String typeValue;

    /**
     * 类型名称
     *
     * @mbggenerated
     */
    private String typeName;

    /**
     * 值
     *
     * @mbggenerated
     */
    private String value;

    /**
     * 名字
     *
     * @mbggenerated
     */
    private String name;

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

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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