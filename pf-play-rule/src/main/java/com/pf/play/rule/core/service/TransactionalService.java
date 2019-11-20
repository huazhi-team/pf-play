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
                     VcRewardReceive rewardReceiveModel , VcMemberResource vcMemberResourceModel) throws  Exception;

    void  updateTask(UDayTaskReward record,UTaskHave uTaskHave);

    void  buyTaskUpdateInfo(UTaskHave uTaskHave,VcMemberResource resource,UMasonryListLog   uMasonryLog);

}
