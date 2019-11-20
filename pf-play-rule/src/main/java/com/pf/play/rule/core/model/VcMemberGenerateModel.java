package com.pf.play.rule.core.model;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 19:12
 * @Version 1.0
 */
public class VcMemberGenerateModel {
    /**
     *
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 修改时间
     */
    private Integer updateTime;
    /**
     * 是否有效 1 有效， 2无效
     */
    private Integer isValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
