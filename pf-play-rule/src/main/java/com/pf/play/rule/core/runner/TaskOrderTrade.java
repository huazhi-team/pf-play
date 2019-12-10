package com.pf.play.rule.core.runner;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.core.model.task.TaskOrderTradeModel;
import com.pf.play.rule.core.model.task.base.StatusModel;
import com.pf.play.rule.core.model.violate.OrderViolateModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 订单交易流水的task任务类
 * @Author yoko
 * @Date 2019/12/6 20:22
 * @Version 1.0
 */
@Component
@EnableScheduling
public class TaskOrderTrade {
    private final static Logger log = LoggerFactory.getLogger(TaskOrderTrade.class);

    @Value("${task.limit.num}")
    private int limitNum;

    /**
     * @Description: 计算订单交易流水数据-计算超时
     * <p>
     *     要运算超时的交易状态：2正常进行中，4确认已付款（买家等待）
     *     状态2：表示卖家已经支付，如果买家没有进行确认付款，则要纪录买家一次违约，并且把锁住卖家的钻石进行解冻
     *     状态4：表示买家已经在指定的时间内付款完毕，如果卖家没有进行确认收款，则要纪录卖家一次违约，并且把订单进行冻结，等待人工处理
     * </p>
     * @author yoko
     * @date 2019/12/6 20:25
    */
    @Scheduled(cron = "1 * * * * ?")
    public void orderTradeCheckTimeOver() throws Exception{
        log.info("==============orderTradeCheckTimeOver=====================start");
        // 获取买家确认付款的超时时间
        StrategyModel buyStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
        StrategyModel buyOverTime = ComponentUtil.strategyService.getStrategyModel(buyStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
        // 获取卖家要确认订单收款的超时时间
        StrategyModel sellStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
        StrategyModel sellOverTime = ComponentUtil.strategyService.getStrategyModel(sellStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
        // 查询要跑的数据
        StatusModel statusQuery = PublicMethod.assembleTaskOrderTradeStatusQuery(limitNum);
        List<TaskOrderTradeModel> synchroList = ComponentUtil.taskOrderTradeService.findByCondition(statusQuery);
        for (TaskOrderTradeModel data : synchroList){
            //锁住这个订单交易流水
            String lockKey = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_TRADE, data.getId());
            boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
            if (flagLock){
                if (data.getTradeStatus() == ServerConstant.TradeStatusEnum.ACTION.getType()){
                    // 买家需要确认支付
                    // 判断买家需要确认支付是否超时
                    int differMinute = DateUtil.dateSubtractBySystemTime(data.getCreateTime());
                    if (differMinute >= Integer.parseInt(buyOverTime.getStgValue())){
                        // 买家超时：A.纪录买家超时违约。 B.修改订单超时状态。 C.卖家钻石解冻 D.订单交易流水的runStatus状态更新成完成

                        // 组装违约纪录数据
                        OrderViolateModel orderViolateModel = PublicMethod.assembleOrderViolateData(data, ServerConstant.ViolateTypeEnum.BUYER_UNPAID.getType(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE, null, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
                        // 组装订单超时的数据
                        OrderModel orderModel = PublicMethod.assembleOrderOverTimeData(data);
                        // 组装卖家解冻数据
                        ConsumerModel consumerModel = PublicMethod.assembleConsumerThawDiamonds(data);
                        // 组装更改运行状态的数据
                        StatusModel statusModel = PublicMethod.assembleUpdateStatusModel(data.getId(), ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
                        ComponentUtil.taskOrderTradeService.taskActoinByBuy(orderViolateModel, orderModel, consumerModel, statusModel);
                    }else{
                        // #发送提醒信息：提醒买家及时支付

                    }
                }else if (data.getTradeStatus() == ServerConstant.TradeStatusEnum.PAY.getType()){
                    // 卖家需要确认收款
                }

                // 解锁
                ComponentUtil.redisIdService.delLock(lockKey);
            }
        }
    }
}