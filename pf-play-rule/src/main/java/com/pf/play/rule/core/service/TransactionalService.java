package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.*;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/19 10:56
 * @Version 1.0
 */

public interface TransactionalService<T> extends BaseService<T> {
    void registerAdd(VcMember memberModel, VcAccountRelation accountRelationModel, VcThirdParty vcThirdPartyModel,
                     VcRewardReceive rewardReceiveModel , VcMemberResource vcMemberResourceModel,UMasonrySummary uMasonrySummary,UTaskHave  uTaskHave) throws  Exception;

    void  updateTask(UDayTaskReward record,UTaskHave uTaskHave);

    void  buyTaskUpdateInfo(UTaskHave uTaskHave,VcMemberResource resource,UMasonryListLog   uMasonryLog,
                              UvitalityValueList my,UvitalityValueList uq,UMasonrySummary uMasonrySummary);


    void  receiveTaskUpdateInfo(UTaskHave uTaskHave,VcRewardReceive resource);


    void  updataActiveValue(VcMemberResource  resource, VcMember vcMember);

    void  insertDailyTaskStat(UdailyTaskStat  udailyTaskStat,UNumberTypeLog uNumberTypeLog);

    void  updateDailyTaskStat(UdailyTaskStat  udailyTaskStat,UNumberTypeLog uNumberTypeLog);

    void  addMasonryListLog(UMasonryListLog   sendUMasonryListLog ,
                                UMasonryListLog   receiptUMasonryListLog,
                                    UMasonryListLog   procedUMasonryListLog,
                                        VcMemberResource vcMemberResourceCut,
                                            VcMemberResource  vcMemberResourceAdd);

    void  updateMyActiveValue(VcMemberResource resource,VcMember vcMember);

    void  gratitudeupdateMyActiveValue(UMasonryListLog uMasonryListLog, UMasonryListLog taskTaskLog,UMasonryListLog realNameLog,VcMemberResource resource);

    void  realNameInfo( USubReward  uSubReward,VcMember  updateVcMember,VcMemberResource  vcMemberResource);

}
