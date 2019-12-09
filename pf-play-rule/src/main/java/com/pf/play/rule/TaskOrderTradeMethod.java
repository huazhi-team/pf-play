package com.pf.play.rule;

import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.task.base.StatusModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单交易流水的Task的公共方法类
 * @Author yoko
 * @Date 2019/12/6 22:35
 * @Version 1.0
 */
public class TaskOrderTradeMethod {

    /**
     * @Description: 组装查询订单交易流水的task任务的查询条件
     * @param limitNum
     * @return StatusModel
     * @author yoko
     * @date 2019/12/6 22:48
    */
    public static StatusModel assembleTaskOrderTradeStatusQuery(int limitNum){
        StatusModel resBean = new StatusModel();
        resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.RUN_NUM_FIVE);
        resBean.setRunStatus(ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
        // 交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ServerConstant.TradeStatusEnum.ACTION.getType());
        statusList.add(ServerConstant.TradeStatusEnum.PAY.getType());
        resBean.setStatusList(statusList);
        resBean.setLimitNum(limitNum);
        return resBean;
    }
}
