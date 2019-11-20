package com.pf.play.rule.core.model;

/**
 * @author df
 * @Description:渠道信息的实体属性Bean
 * @create 2019-05-27 16:42
 **/
public class ChannelInfoModel {

    /**
     * 自增主键ID
     */
    private long id;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 增值的cp_id:对应增值分配的cpId
     */
    private long cpId;

    /**
     * 增值的key:对应增值分给的key
     */
    private String cpKey;

    /**
     * 是否有效：0初始化，1失效/删除
     */
    private int yn;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public long getCpId() {
        return cpId;
    }

    public void setCpId(long cpId) {
        this.cpId = cpId;
    }

    public String getCpKey() {
        return cpKey;
    }

    public void setCpKey(String cpKey) {
        this.cpKey = cpKey;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }
}
