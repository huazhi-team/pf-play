package com.pf.play.rule;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.model.protocol.response.synchr.MemberResp;
import com.pf.play.rule.core.model.*;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/9 16:35
 * @Version 1.0
 */
public class SynchroMethod {

    /**
     * @Description: 封装点赞同步
     * @param memberId
     * @return com.pf.play.rule.core.model.UNumberTypeLog
     * @author long
     * @date 2019/12/9 16:40
     */
    public  static UNumberTypeLog getUNumberType1(Integer memberId){
        DateModel   dateModel = TaskMethod.getDate();
        UNumberTypeLog   uNumberTypeLog = new UNumberTypeLog();
        BeanUtils.copy(dateModel,uNumberTypeLog);
        uNumberTypeLog.setMemberId(memberId);
        uNumberTypeLog.setType(1);
        return uNumberTypeLog;
    }


    /**
     * @Description: 封装点击商品同步
     * @param memberId
     * @return com.pf.play.rule.core.model.UNumberTypeLog
     * @author long
     * @date 2019/12/9 16:40
     */
    public  static UNumberTypeLog getUNumberType2(Integer memberId){
        DateModel   dateModel = TaskMethod.getDate();
        UNumberTypeLog   uNumberTypeLog = new UNumberTypeLog();
        BeanUtils.copy(dateModel,uNumberTypeLog);
        uNumberTypeLog.setMemberId(memberId);
        uNumberTypeLog.setType(2);
        return uNumberTypeLog;
    }

    /**
     * @Description: 准备用户每天任务统计表
     * @param memberId
     * @return com.pf.play.rule.core.model.UdailyTaskStat
     * @author long
     * @date 2019/12/9 18:10
     */
    public  static  UdailyTaskStat  getUdailyTaskStat(Integer memberId){
        Date   date  = new Date();
        UdailyTaskStat   udailyTaskStat =  new UdailyTaskStat();
        udailyTaskStat.setMemberId(memberId);
        udailyTaskStat.setCurday(DateUtil.getDayNumber(date));
        return  udailyTaskStat;
    }

    /**
     * @Description: 如果没有数据的情况下先添加下表信息进行
     * @param memberId
     * @return com.pf.play.rule.core.model.UdailyTaskStat
     * @author long
     * @date 2019/12/9 18:32
     */
    public  static  UdailyTaskStat getInsertUdailyTaskStat(Integer memberId,Integer type){
        DateModel   dateModel = TaskMethod.getDate();
        UdailyTaskStat record  =  new UdailyTaskStat();
        BeanUtils.copy(dateModel,record);
        record.setMemberId(memberId);
        if(type==1){
            record.setAcceptNumber(1);
        }else if(type==2){
            record.setLookCommodityNum(1);
        }
        return   record;
    }


    /**
     * @Description: 如果有数据的情况下修改表下表信息进行
     * @param memberId
    * @param type
     * @return com.pf.play.rule.core.model.UdailyTaskStat
     * @author long
     * @date 2019/12/9 19:46
     */
    public  static  UdailyTaskStat getUqdateUdailyTaskStat(Integer memberId,Integer type){
        UdailyTaskStat record  =  new UdailyTaskStat();
        record.setCurday(DateUtil.getDayNumber(new Date()));
        record.setMemberId(memberId);
        record.setUpdateTime(new Date());
        if(type==1){
            record.setAcceptNumber(1);
        }else if(type==2){
            record.setLookCommodityNum(1);
        }
        return   record;
    }

    /**
     * @Description: VcMember 转 SyncPrarentId
     * @param vcMember
     * @return java.lang.String
     * @author long
     * @date 2019/12/10 10:48
     */
    public  static  String   toSyncPrarentId(VcMember vcMember){
        SyncPrarentId  syncPrarentId = new SyncPrarentId();
        syncPrarentId.setMember_id(vcMember.getMemberId()+"");
        syncPrarentId.setParent_id(vcMember.getSuperiorId()+"");
        return   JSON.toJSONString(syncPrarentId);
    }
    /**
     * @Description: 检查是否是正确的信息
     * @param memberId
     * @return boolean
     * @author long
     * @date 2019/12/10 12:02
     */
    public  static  boolean   cheakIsMemberId(String memberId){
        boolean  flag =  false ;
        try{
            if(!StringUtils.isBlank(memberId)&& Integer.parseInt(memberId)>0){
                flag = true ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return   flag;
    }

    public  static MemberResp ChangToMemberResp(VcMemberResource vcMemberResource){
        MemberResp  memberResp =  new MemberResp();
        BeanUtils.copy(vcMemberResource,memberResp);
        return  memberResp;
    }



}
