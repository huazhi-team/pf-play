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
    @Autowired
    private UEmpiricalValueListMapper  uEmpiricalValueListMapper ;






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
                            VcRewardReceive rewardReceiveModel, VcMemberResource vcMemberResourceModel,UMasonrySummary uMasonrySummary,
                                UTaskHave  uTaskHave,VcMemberResource  vcMemberResource)throws  Exception {

        vcMemberMapper.insertSelective(memberModel);
        vcAccountRelationMapper.insertSelective(accountRelationModel);
        vcThirdPartyMapper.insertSelective(vcThirdPartyModel);
        vcRewardReceiveMapper.insertSelective(rewardReceiveModel);
        vcMemberResourceMapper.insertSelective(vcMemberResourceModel);
        ComponentUtil.registerService.userRegisterReward(memberModel.getMemberId());
        uMasonrySummaryMapper.insertSelective(uMasonrySummary);
        uTaskHaveMapper.insertSelective(uTaskHave);
        vcMemberResourceMapper.updateAllPeople(vcMemberResource);
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
                                        UMasonryListLog uMasonryLog,UvitalityValueList my,UvitalityValueList uq,UMasonrySummary uMasonrySummary,VcMemberResource uqEmpirical,UEmpiricalValueList uEmpiricalValueList) {
        uTaskHaveMapper.insertSelective(uTaskHave);         //任务拥有表添加
        uMasonryListLogMapper.insertSelective(uMasonryLog); //砖石明细表
        uVitalityValueListMapper.insertSelective(my);       //自己加活力值明细
        uVitalityValueListMapper.insertSelective(uq);       //上级加活力值明细
        vcMemberResourceMapper.updateByPrimaryKeySelective(resource);   // 修改资源表信息
        uMasonrySummaryMapper.updateByPrimaryKeySelective(uMasonrySummary);  //更新砖石汇总表
        uEmpiricalValueListMapper.insertSelective(uEmpiricalValueList); //添加任务经验值明细表
        vcMemberResourceMapper.updateEmpiricalValue(uqEmpirical); //更新会员等级以及经验值

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
    public void gratitudeupdateMyActiveValue(UMasonryListLog uMasonryListLog, UMasonryListLog taskTaskLog,UMasonryListLog realNameLog,VcMemberResource resource,UTaskHave uTaskHave) {
        if(uMasonryListLog!=null){
            uMasonryListLogMapper.insertSelective(uMasonryListLog);
        }
        if(realNameLog!=null){
            uMasonryListLogMapper.insertSelective(realNameLog);
        }
        uMasonryListLogMapper.insertSelective(taskTaskLog);
        vcMemberResourceMapper.updateByMasonry(resource);
        uTaskHaveMapper.updateTaskCount(uTaskHave);
    }

    @Override
    public void realNameInfo(USubReward uSubReward, VcMember updateVcMember, VcMemberResource vcMemberResource,VcMemberResource uqResource,
                                UEmpiricalValueList uEmpiricalValueList,VcMemberResource uqEmpirical,UvitalityValueList my,UvitalityValueList uq) {
        uSubRewardMapper.insertSelective(uSubReward); //上级奖励表
        vcMemberResourceMapper.updateRealName(vcMemberResource); //修改本会员的信息
        vcMemberMapper.updateByPrimaryKeySelective(updateVcMember); //修改是否实名制了
        vcMemberResourceMapper.updateUqPeople(uqResource);//上级所有的人信息
        uEmpiricalValueListMapper.insertSelective(uEmpiricalValueList); //添加实名制明细表
        vcMemberResourceMapper.updateEmpiricalValue(uqEmpirical); //更新会员等级以及经验值
//        uVitalityValueListMapper.insertSelective(my);       //自己加活力值明细
        uVitalityValueListMapper.insertSelective(uq);       //上级加活力值明细
    }

    @Override
    public void taskExpireUpdateInfo(UTaskHave uTaskHave,UvitalityValueList uVitalityValueList) {
        //修改拥有的状态
        uTaskHaveMapper.updateCurrentState(uTaskHave);
        //添加一条减去的历史
        uVitalityValueListMapper.insertSelective(uVitalityValueList);
    }

    @Override
    public void myActiveValueUpdate(UMasonryListLog masonryLog, UMasonrySummary uMasonrySummary, VcMemberResource resource) {
        uMasonryListLogMapper.insertSelective(masonryLog); //砖石明细表
        uMasonrySummaryMapper.updateByPrimaryKeySelective(uMasonrySummary); //砖石汇总表修改
        vcMemberResourceMapper.updateByActiveValue(resource);//用戶信息的更新
    }
}
