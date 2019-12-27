package com.pf.play.rule;

import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.task.TaskAlipayNotifyModel;
import com.pf.play.rule.core.model.task.base.StatusModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description 定时任务：大杂烩的公共方法类
 * @Author yoko
 * @Date 2019/12/27 22:51
 * @Version 1.0
 */
public class TaskHodgepodgeMethod {
    private final static Logger log = LoggerFactory.getLogger(TaskHodgepodgeMethod.class);

    /**
     * @Description: 组装查询支付宝订单同步的数据的查询条件
     * @param limitNum
     * @return StatusModel
     * @author yoko
     * @date 2019/12/6 22:48
     */
    public static StatusModel assembleTaskAlipayNotifyStatusQuery(int limitNum){
        StatusModel resBean = new StatusModel();
        resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.RUN_NUM_FIVE);
        resBean.setRunStatus(ServerConstant.PUBLIC_CONSTANT.RUN_STATUS_THREE);
        resBean.setLimitNum(limitNum);
        return resBean;
    }

    /**
     * @Description: 组装更新用户是否支付实名制费用的数据
     * @param taskAlipayNotifyModel - 阿里支付宝订单的同步数据信息
     * @return Map
     * @author yoko
     * @date 2019/12/27 23:02
    */
    public static Map<String, Object> assembleUpdateTaskAlipayNotifyStatus(TaskAlipayNotifyModel taskAlipayNotifyModel){
        Map<String, Object> map = new HashMap<>();
        if (taskAlipayNotifyModel != null && taskAlipayNotifyModel.getMemberId() != null){
            map.put("memberId", taskAlipayNotifyModel.getMemberId());
            map.put("isPay", ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
        }else {
            return null;
        }
        return map;
    }

    /**
     * @Description: 组装更改运行状态的数据
     * @param id - 主键ID
     * @param runStatus - 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     * @return StatusModel
     * @author yoko
     * @date 2019/12/10 10:42
     */
    public static StatusModel assembleUpdateStatusModel(long id, int runStatus){
        StatusModel resBean = new StatusModel();
        resBean.setId(id);
        resBean.setRunStatus(runStatus);
        if (runStatus == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){
            // 表示失败：失败则需要运行次数加一
            resBean.setRunNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        }
        return resBean;
    }


}
