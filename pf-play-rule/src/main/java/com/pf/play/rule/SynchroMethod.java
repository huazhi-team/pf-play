package com.pf.play.rule;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.model.protocol.request.give.SendGiftResp;
import com.pf.play.model.protocol.response.synchr.MemberResp;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.util.ComponentUtil;
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
    /**
     * @Description: SendGiftResp  参数验证是否能行
     * @param sendGiftResp
     * @return boolean
     * @author long
     * @date 2019/12/10 18:40
     */
    public  static boolean chechParameter(SendGiftResp sendGiftResp){
        boolean   flag = false ;
        if(!StringUtils.isBlank(sendGiftResp.getPayPw())||
                sendGiftResp.getReceiptMemberId()>0||
                    sendGiftResp.getSendMemberId()>0||
                        sendGiftResp.getMasonryCount()>0){
            flag = true ;
        }
        return  flag;
    }

    /**
     * @Description: 会员id+ password 查询信息 转换VcMember
     * @param memberId
    * @param passWord
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/12/10 18:58
     */
    public static  VcMember    changToVcMember(Integer memberId,String passWord){
        VcMember  vcMember  = new VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setPayPassword(passWord);
        return vcMember;
    }


    /**
     * @Description: 用户砖石明细需要信息
     * @param memberId  用户id
    * @param taskId    任务id
    * @param type      明细类型
    * @param SymbolType 1 是加  2 是减
    * @param needResource 砖石数
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/11/21 17:35
     */
    public   static  UMasonryListLog   changeUMasonryListLog(Integer memberId,Integer type,Integer SymbolType, Double needResource){
        UMasonryListLog   uMasonryListLog  =  new UMasonryListLog();
        DateModel dateModel= TaskMethod.getDate();
        BeanUtils.copy(dateModel,uMasonryListLog);
        uMasonryListLog.setMemberId(memberId);
        uMasonryListLog.setType(type);
        uMasonryListLog.setSymbolType(SymbolType);
        uMasonryListLog.setMasonryNum(needResource);
        return uMasonryListLog;
    }

    /**
     * @Description: 根据MemberId 冻结 砖石信息
     * @param memberId
    * @param masonry
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/10 22:22
     */
    public   static  VcMemberResource  changUpdateResource(Integer memberId,Double masonry){
        VcMemberResource  vcMemberResource = new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setDayMasonry(masonry);
        vcMemberResource.setFrozenMasonry(masonry);
        return   vcMemberResource;
    }

    public   static  VcMemberResource  changUpdateResourceMasonry(Integer memberId,Double masonry){
        VcMemberResource  vcMemberResource = new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setDayMasonry(masonry);
        return   vcMemberResource;
    }

    /**
     * @Description: 如果有数据的情况下修改表下表信息进行
     * @param memberId
    * @param type
     * @return com.pf.play.rule.core.model.UdailyTaskStat
     * @author long
     * @date 2019/12/9 19:46
     */
    public  static  UdailyTaskStat getUqdateUdailyTaskStat(Integer memberId){
        UdailyTaskStat record  =  new UdailyTaskStat();
        record.setCurday(DateUtil.getDayNumber(new Date()));
        record.setMemberId(memberId);
        record.setUpdateTime(new Date());
        record.setAcceptNumber(0);
        record.setLookCommodityNum(0);
        return   record;
    }

}
