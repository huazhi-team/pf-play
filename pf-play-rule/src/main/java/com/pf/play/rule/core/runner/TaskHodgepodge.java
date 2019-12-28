package com.pf.play.rule.core.runner;

import com.pf.play.rule.TaskHodgepodgeMethod;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.task.TaskAlipayNotifyModel;
import com.pf.play.rule.core.model.task.base.StatusModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 定时任务：大杂烩
 * @Author yoko
 * @Date 2019/12/6 20:22
 * @Version 1.0
 */
@Component
@EnableScheduling
public class TaskHodgepodge {
    private final static Logger log = LoggerFactory.getLogger(TaskHodgepodge.class);

    @Value("${task.limit.num}")
    private int limitNum;

//    /**
//     * 早上10点启动
//     */
//    @Scheduled(cron = "0 0 10 * * ?")
    //凌晨1点启动
    @Scheduled(cron = "0 0 1 * * ?")
    public void job1(){
        System.out.println("定时任务：" + new Date());
    }

    /**
     * @Description: 阿里云支付：跑阿里云实名认证的已经成功的订单
     * <p>
     *     抓取已经成功的阿里云订单信息，然后把同步过来的订单号查询我方原始数据订单。
     *     找出此订单的用户ID。
     *     修改这个用户的is_pay的值。
     * </p>
     * @author yoko
     * @date 2019/12/27 21:30
    */
//    @Scheduled(cron = "1 * * * * ?")
    @Scheduled(fixedDelay = 1000) // 每秒执行
    public void taskAlipay() throws Exception{
        log.info("TaskHodgepodge.taskAlipay()------------------进来了!");
        // 查询要跑的数据
        StatusModel statusQuery = TaskHodgepodgeMethod.assembleTaskAlipayNotifyStatusQuery(limitNum);
        List<TaskAlipayNotifyModel> dataList = ComponentUtil.taskHodgepodgeService.getTaskAlipayNotify(statusQuery);
        if (dataList != null && dataList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            for (TaskAlipayNotifyModel dataModel : dataList){
                //锁住这个订单交易流水
                String lockKey = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_TASK_ALIPAY_NOTIFY, dataModel.getId());
                boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
                if (flagLock){
                    try {
                        // 组装要更新用户是否支付实名认证的数据
                        Map<String, Object> map = TaskHodgepodgeMethod.assembleUpdateTaskAlipayNotifyStatus(dataModel);
                        if (map != null){
                            // 更新用户是否支付实名制费用
                            ComponentUtil.taskHodgepodgeService.updateConsumerIsPay(map);
                            // 组装更改运行状态的数据
                            StatusModel statusModel = TaskHodgepodgeMethod.assembleUpdateStatusModel(dataModel.getId(), ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
                            ComponentUtil.taskHodgepodgeService.updateTaskAlipayNotifyStatus(statusModel);
                        }else {
                            // 更新此次task的状态：更新成失败
                            StatusModel statusModel = TaskHodgepodgeMethod.assembleUpdateStatusModel(dataModel.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
                            ComponentUtil.taskHodgepodgeService.updateTaskAlipayNotifyStatus(statusModel);
                        }
                    }catch (Exception e){
                        log.error(String.format("this TaskHodgepodge.taskAlipay() is error , the dataId=%s !", dataModel.getId()));
                        e.printStackTrace();
                        // 更新此次task的状态：更新成失败
                        StatusModel statusModel = TaskHodgepodgeMethod.assembleUpdateStatusModel(dataModel.getId(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
                        ComponentUtil.taskHodgepodgeService.updateTaskAlipayNotifyStatus(statusModel);
                    }finally {
                        // 解锁
                        ComponentUtil.redisIdService.delLock(lockKey);
                    }
                }

            }
        }
        log.info("TaskHodgepodge.taskAlipay()------------------结束了!");
    }
}
