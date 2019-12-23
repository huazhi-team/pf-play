package com.pf.play.rule.core.runner;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.DisVitalityValue;
import com.pf.play.rule.core.model.UMasonryListLog;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.core.singleton.EmpiricalVitalitySingleton;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description 按照用户等级、以及趣红人收取的手续费来赠送用户钻石
 * <p>
 *     1.查询昨天趣红人收取到的所有手续费（钻石数）
 *     2.查询策略里面手续费的百分之多少分给用户
 *     3.根据达人等级查询出相对应的用户
 *     4.具体赠送用户钻石、并且把赠送纪录插入到表u_masonry_list_log中
 *     赠送用户的钻石算法：昨天收取的手续费X要分出的钻石比例（策略里面配置）X达人等级分出的占比/达人等级的人数=要赠送的钻石数
 * </p>
 * @Author yoko
 * @Date 2019/12/23 14:20
 * @Version 1.0
 */
@Component
@EnableScheduling
public class TaskGiveDiamond {
    private final static Logger log = LoggerFactory.getLogger(TaskGiveDiamond.class);

//    @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "9 * * * * ?")
    public void giveDiamond(){
        try{
            // 昨天
            int yesterDay = DateUtil.getIntYesterday();
            String oneDayServiceCharge = ComponentUtil.tradeService.getOneDayServiceCharge(yesterDay);
            log.info("-----------------oneDayServiceCharge:" + oneDayServiceCharge);
            if (Double.parseDouble(oneDayServiceCharge) > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                // 手续费分出给用户的比例
                StrategyModel strategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SERVICE_CHARGE_RATIO.getStgType());
                StrategyModel strategyModel = ComponentUtil.strategyService.getStrategyModel(strategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
                // 具体要分的钻石=手续费X手续费分出给用户的比例
                String stgRatio = StringUtil.getBigDecimalDivide(strategyModel.getStgValue(), String.valueOf(100));
                String giveServiceChargeNum = StringUtil.getMultiply(oneDayServiceCharge, stgRatio);
                // 等级达人分配的奖励比例
                List<DisVitalityValue> disVitalityValueList = EmpiricalVitalitySingleton.getInstance().getDisVitalityValue();
                for (DisVitalityValue disVitalityValue : disVitalityValueList){
                    if (disVitalityValue.getDarenLevel() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                        // 具体每个等级总共要分的钻石=具体要分的钻石X达人等级分出的占比
                        String everyLevelNum = StringUtil.getMultiply(giveServiceChargeNum, String.valueOf(disVitalityValue.getRewardNum()/100));
                        List<ConsumerModel> consumerList = ComponentUtil.consumerFixedService.getConsumerByDarenLevel(disVitalityValue.getDarenLevel());
                        if (consumerList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                            // 每个人分得的钻石个数 = 具体每个等级总共要分的钻石/这个等级下总共多少人
                            String everyConsumerNum = StringUtil.getBigDecimalDivide(everyLevelNum, String.valueOf(consumerList.size()));
                            for (ConsumerModel consumerModel : consumerList){
                                // 给用户添加钻石
                                ConsumerModel addConsumerDiamond = PublicMethod.assembleConsumerAddMasonry(consumerModel.getMemberId(), everyConsumerNum);
                                ComponentUtil.consumerFixedService.updateConsumerAddMasonry(addConsumerDiamond);
                                // 添加用户赠送的流水
                                int taskType = TaskMethod.gettaskType(disVitalityValue.getDarenLevel());
                                UMasonryListLog uMasonryListLog = TaskMethod.changeUMasonryListLog(consumerModel.getMemberId().intValue(),null, taskType,Constant.TASK_SYMBOL_TYPE1,Double.valueOf(everyConsumerNum));
                                ComponentUtil.taskService.insertUMasonryListLog(uMasonryListLog);
                            }

                        }

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
