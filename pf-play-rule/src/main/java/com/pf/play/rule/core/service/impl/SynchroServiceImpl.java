package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.StrategyMapper;
import com.pf.play.rule.core.mapper.UNumberTypeLogMapper;
import com.pf.play.rule.core.mapper.UdailyTaskStatMapper;
import com.pf.play.rule.core.mapper.VcMemberResourceMapper;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.SynchroService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
