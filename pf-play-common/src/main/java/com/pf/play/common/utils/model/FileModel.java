package com.pf.play.common.utils.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author df
 * @Description:文件属性Bean
 * @create 2019-08-14 15:28
 **/
public class FileModel {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户唯一标识
     */
    private String usid;

    /**
     * 活跃时间
     */
    private String activeTime;

    /**
     * 合作方ID
     */
    private String hzfId = FileConstant.HZF_ID;

    /**
     * CPID
     */
    private String cpId = FileConstant.CP_ID;

    /**
     * 渠道ID
     */
    private String qdId = FileConstant.QD_ID;

    /**
     * 产品ID
     */
    private String chanpId = FileConstant.CHAN_P_ID;

    /**
     * 业务名称
     */
    private String ywName = FileConstant.YW_NAME;

    /**
     * 活跃行为类型
     */
    private String hylx = "0";

    /**
     * 接入媒介
     */
    private String jrmj = "1";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getActiveTime() {
        if (!StringUtils.isBlank(activeTime)){
            String str = activeTime.replace("-","").replace(":","").replace(" ","") + "00";
            return str;
        }else{
            return activeTime;
        }



    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getHzfId() {
        return hzfId;
    }

    public void setHzfId(String hzfId) {
        this.hzfId = hzfId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getQdId() {
        return qdId;
    }

    public void setQdId(String qdId) {
        this.qdId = qdId;
    }

    public String getChanpId() {
        return chanpId;
    }

    public void setChanpId(String chanpId) {
        this.chanpId = chanpId;
    }

    public String getYwName() {
        return ywName;
    }

    public void setYwName(String ywName) {
        this.ywName = ywName;
    }

    public String getHylx() {
        return hylx;
    }

    public void setHylx(String hylx) {
        this.hylx = hylx;
    }

    public String getJrmj() {
        return jrmj;
    }

    public void setJrmj(String jrmj) {
        this.jrmj = jrmj;
    }
}
