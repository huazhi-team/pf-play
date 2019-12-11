package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.SynchroService;
import com.pf.play.rule.core.singleton.RegisterSingleton;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/9 16:32
 * @Version 1.0
 */
@Service
public class SynchroServiceImpl<T> extends BaseServiceImpl<T> implements SynchroService<T> {
    @Autowired
    private UNumberTypeLogMapper uNumberTypeLogMapper;

    @Autowired
    private UdailyTaskStatMapper udailyTaskStatMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void addGive(Integer memberId) {
        UdailyTaskStat record  =  SynchroMethod.getUdailyTaskStat(memberId);
        UdailyTaskStat  statModel = udailyTaskStatMapper.selectByMemberIdDay(record);  //每天用户名明细信息
        UNumberTypeLog uNumberTypeLog   = SynchroMethod.getUNumberType1(memberId );
        if(statModel != null){
            UdailyTaskStat record1  = SynchroMethod.getUqdateUdailyTaskStat(memberId,1);
            ComponentUtil.transactionalService.updateDailyTaskStat(record1,uNumberTypeLog);
        }else{
            UdailyTaskStat record1  = SynchroMethod.getInsertUdailyTaskStat(memberId,1);
            ComponentUtil.transactionalService.insertDailyTaskStat(record1,uNumberTypeLog);
        }
    }

    @Override
    public void addGoods(Integer memberId) {
        UdailyTaskStat record  =  SynchroMethod.getUdailyTaskStat(memberId);
        UdailyTaskStat  statModel = udailyTaskStatMapper.selectByMemberIdDay(record);  //每天用户名明细信息
        UNumberTypeLog uNumberTypeLog   = SynchroMethod.getUNumberType2(memberId );
        if(statModel != null){
            UdailyTaskStat record1  = SynchroMethod.getUqdateUdailyTaskStat(memberId,2);
            ComponentUtil.transactionalService.updateDailyTaskStat(record1,uNumberTypeLog);
        }else{
            UdailyTaskStat record1  = SynchroMethod.getInsertUdailyTaskStat(memberId,2);
            ComponentUtil.transactionalService.insertDailyTaskStat(record1,uNumberTypeLog);
        }
    }

    @Override
    public VcMemberResource queryVcMemberResource(VcMemberResource vcMemberResource) {
        VcMemberResource  vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        return vcMemberResource1;
    }


    @Override
    public int checkSendInfo(Integer sendMemberId, Integer receiptMemberId, String passWord) {
        int  flag =0  ;
        VcMember  vcMember  = SynchroMethod.changToVcMember(sendMemberId,passWord);
        //先看 sendMemberId 和  password  正确
        VcMember  rs   = vcMemberMapper.selectByPrimaryKey(vcMember);
        if(rs==null){
            flag = -1;
            return flag;
        }

        VcMember  vcMember1  = TaskMethod.changvcMember(receiptMemberId);
        rs   =  vcMemberMapper.selectByMemberId(vcMember1);
        if(rs==null){
            flag = -2;
        }
        return flag;
    }

    @Override
    public boolean chechMemberResource(Integer sendMemberId,Double  masonry) {
        boolean  flag  =  true ;
        VcMemberResource  vcMemberResource =  TaskMethod.changvcMemberResource(sendMemberId) ;
        VcMemberResource  vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        if (vcMemberResource1 ==null){
            return false;
        }
        if(vcMemberResource1.getDayMasonry()<masonry){
            return false;
        }
        return flag;
    }

    @Override
    public int addMemberResource(Integer sendMemberId, Integer receiptMemberId, Double masonry) {
//        UMasonryListLog
//        Double   masonry
        Double    proced =0D;
        List<SysTypeDictionary>  procedTypeList = RegisterSingleton.getInstance().getProcedType();
        if(procedTypeList.size()==0){
            return 0;
        }else{
            proced  = Double.parseDouble(procedTypeList.get(0).getValue());
        }

        VcMemberResource  vcMemberResourceCut  =  SynchroMethod.changUpdateResource(sendMemberId,masonry);
        VcMemberResource  vcMemberResourceAdd  =  SynchroMethod.changUpdateResource(receiptMemberId,masonry*(1-proced));
        UMasonryListLog   sendUMasonryListLog      =   SynchroMethod.changeUMasonryListLog(sendMemberId,Constant.TASK_TYPE3 ,Constant.TASK_SYMBOL_TYPE2,masonry);
        UMasonryListLog   receiptUMasonryListLog   =   SynchroMethod.changeUMasonryListLog(receiptMemberId, Constant.TASK_TYPE4,Constant.TASK_SYMBOL_TYPE1,masonry*(1-proced));
        UMasonryListLog   procedUMasonryListLog    =   SynchroMethod.changeUMasonryListLog(0, Constant.TASK_TYPE10,Constant.TASK_SYMBOL_TYPE1,masonry*proced);


        String lockKey_send = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, sendMemberId);
        boolean send = ComponentUtil.redisIdService.lock(lockKey_send);
        //锁住这个买家的用户ID
        String lockKey_receipt = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, receiptMemberId);
        boolean receipt = ComponentUtil.redisIdService.lock(lockKey_receipt);

        if(send && receipt){
            ComponentUtil.transactionalService.addMasonryListLog(sendUMasonryListLog,receiptUMasonryListLog,procedUMasonryListLog,vcMemberResourceCut,vcMemberResourceAdd);
        }else{
            return 0;
        }
        ComponentUtil.redisIdService.delLock(lockKey_send);
        ComponentUtil.redisIdService.delLock(lockKey_receipt);
        return 1;
    }
}
