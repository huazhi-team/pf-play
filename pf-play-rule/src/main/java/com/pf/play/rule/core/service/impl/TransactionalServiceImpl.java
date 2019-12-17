package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.TransactionalService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


/**
 * @Description 所有需要事务回滚的方法都放这里面
 * @Author long
 * @Date 2019/11/19 11:02
 * @Version 1.0
 */
@Service
@Transactional
public class TransactionalServiceImpl<T> extends BaseServiceImpl<T> implements TransactionalService<T> {
    @Autowired
    private UTaskHaveMapper uTaskHaveMapper;

    @Autowired
    private UDayTaskRewardMapper uDayTaskRewardMapper;
    @Autowired
    private VcAccountRelationMapper vcAccountRelationMapper;

    @Autowired
    private VcThirdPartyMapper vcThirdPartyMapper;

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;

    @Autowired
    private VcRewardReceiveMapper  vcRewardReceiveMapper ;
    @Autowired
    private UMasonryListLogMapper  uMasonryListLogMapper ;
    @Autowired
    private UvitalityValueListMapper  uVitalityValueListMapper ;

    @Autowired
    private UMasonrySummaryMapper  uMasonrySummaryMapper ;

    @Autowired
    private UdailyTaskStatMapper  udailyTaskStatMapper ;

    @Autowired
    private UNumberTypeLogMapper  uNumberTypeLogMapper ;

    @Autowired
    private USubRewardMapper  uSubRewardMapper ;






    @Override
    public BaseDao<T> getDao() {
        return vcRewardReceiveMapper;
    }
    /**
     * @Description: 用户注册信息同一个事物进行添加
     * @param memberModel
    * @param accountRelationModel
    * @param vcThirdPartyModel
    * @param rewardReceiveModel
    * @param vcMemberResourceModel
     * @return void
     * @author long
     * @date 2019/11/19 11:48
     */
    @Override
    public void registerAdd(VcMember memberModel, VcAccountRelation accountRelationModel, VcThirdParty vcThirdPartyModel,
                            VcRewardReceive rewardReceiveModel, VcMemberResource vcMemberResourceModel,UMasonrySummary uMasonrySummary,UTaskHave  uTaskHave)throws  Exception {

        vcMemberMapper.insertSelective(memberModel);
        vcAccountRelationMapper.insertSelective(accountRelationModel);
        vcThirdPartyMapper.insertSelective(vcThirdPartyModel);
        vcRewardReceiveMapper.insertSelective(rewardReceiveModel);
        vcMemberResourceMapper.insertSelective(vcMemberResourceModel);
        ComponentUtil.registerService.userRegisterReward(memberModel.getMemberId());
        uMasonrySummaryMapper.insertSelective(uMasonrySummary);
        uTaskHaveMapper.insertSelective(uTaskHave);
    }

    /**
     * @Description: 用户领取当天奖励，需要操做的表信息
     * @param record
    * @param uTaskHave
     * @return void
     * @author long
     * @date 2019/11/19 11:48
     */
    @Override
    public void updateTask(UDayTaskReward record, UTaskHave uTaskHave) {
        uDayTaskRewardMapper.insertSelective(record);
        uTaskHaveMapper.updateByPrimaryKey(uTaskHave);
    }


    @Override
    public void buyTaskUpdateInfo(UTaskHave uTaskHave, VcMemberResource resource,
                                        UMasonryListLog uMasonryLog,UvitalityValueList my,UvitalityValueList uq,UMasonrySummary uMasonrySummary) {
        uTaskHaveMapper.insertSelective(uTaskHave);         //任务拥有表添加
        uMasonryListLogMapper.insertSelective(uMasonryLog); //砖石明细表
        uVitalityValueListMapper.insertSelective(my);       //自己加活力值明细
        uVitalityValueListMapper.insertSelective(uq);       //上级加活力值明细
        vcMemberResourceMapper.updateByPrimaryKeySelective(resource);   // 修改资源表信息
        uMasonrySummaryMapper.updateByPrimaryKeySelective(uMasonrySummary);  //更新砖石汇总表
    }


    @Override
    public void receiveTaskUpdateInfo(UTaskHave uTaskHave, VcRewardReceive resource) {
        uTaskHaveMapper.insertSelective(uTaskHave);
        vcRewardReceiveMapper.updateByPrimaryKeySelective(resource);
    }


    @Override
    public void updataActiveValue(VcMemberResource  resource, VcMember vcMember) {
        vcMemberResourceMapper.updateTeamActive(resource);
        vcMemberResourceMapper.updateHeroActive(vcMember);
        vcMemberResourceMapper.updateAllianceActive(vcMember);
    }

    @Override
    public void insertDailyTaskStat(UdailyTaskStat udailyTaskStat, UNumberTypeLog uNumberTypeLog) {
        udailyTaskStatMapper.insertSelective(udailyTaskStat);
        uNumberTypeLogMapper.insertSelective(uNumberTypeLog);
    }

    @Override
    public void updateDailyTaskStat(UdailyTaskStat udailyTaskStat, UNumberTypeLog uNumberTypeLog) {
        udailyTaskStatMapper.updateByMemberId(udailyTaskStat);
        uNumberTypeLogMapper.insertSelective(uNumberTypeLog);
    }

    @Override
    public void addMasonryListLog(UMasonryListLog sendUMasonryListLog, UMasonryListLog receiptUMasonryListLog,
                                        UMasonryListLog procedUMasonryListLog,VcMemberResource vcMemberResourceCut,
                                                            VcMemberResource  vcMemberResourceAdd) {
        vcMemberResourceMapper.updateCutMasonry(vcMemberResourceCut);
        vcMemberResourceMapper.updateAddMasonry(vcMemberResourceAdd);
        uMasonryListLogMapper.insertSelective(sendUMasonryListLog);
        uMasonryListLogMapper.insertSelective(receiptUMasonryListLog);
        uMasonryListLogMapper.insertSelective(procedUMasonryListLog);
    }

    @Override
    public void updateMyActiveValue(VcMemberResource resource,VcMember vcMember) {
        vcMemberResourceMapper.updateByActiveValue(resource);
        vcMemberResourceMapper.updateHeroActive(vcMember);
        vcMemberResourceMapper.updateAllianceActive(vcMember);
    }

    @Override
    public void gratitudeupdateMyActiveValue(UMasonryListLog uMasonryListLog, UMasonryListLog taskTaskLog,UMasonryListLog realNameLog,VcMemberResource resource) {
        if(uMasonryListLog!=null){
            uMasonryListLogMapper.insertSelective(uMasonryListLog);
        }
        uMasonryListLogMapper.insertSelective(taskTaskLog);
        uMasonryListLogMapper.insertSelective(realNameLog);
        vcMemberResourceMapper.updateByMasonry(resource);
    }

    @Override
    public void realNameInfo(USubReward uSubReward, VcMember updateVcMember, VcMemberResource vcMemberResource) {
        uSubRewardMapper.insertSelective(uSubReward);
        vcMemberResourceMapper.updateRealName(vcMemberResource);
        vcMemberMapper.updateByPrimaryKeySelective(updateVcMember);
    }
}
