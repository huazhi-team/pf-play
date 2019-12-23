package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 用户固定账号的Dao层
 * @Author yoko
 * @Date 2019/11/21 17:25
 * @Version 1.0
 */
@Mapper
public interface ConsumerFixedMapper<T> extends BaseDao<T> {

    /**
     * @Description: 根据条件查询用户基本信息、支付宝信息、钻石信息、等级信息
     * @param model - 查询条件
     * @return
     * @author yoko
     * @date 2019/11/21 19:26
     */
    public ConsumerModel getConsumer(ConsumerModel model);

    /**
     * @Description: 获取用户手续费的方法
     * @param model
     * @return ConsumerModel
     * @author yoko
     * @date 2019/11/29 11:31
     */
    public ConsumerModel getConsumerServiceCharge(ConsumerModel model);


    /**
     * @Description: 修改用户的钻石-冻结
     * @param model
     * @return
     * @author yoko
     * @date 2019/12/2 11:29
     */
    public int updateConsumerMasonry(ConsumerModel model);

    /**
     * @Description: 用户资源加钻石
     * @param model - 用户信息：用户ID、用户要加的钻石个数
     * @return
     * @author yoko
     * @date 2019/12/3 21:36
    */
    public int updateConsumerAddMasonry(ConsumerModel model);

    /**
     * @Description: 用户资源减钻石：这里减的是冻结的钻石
     * @param model - 用户信息：用户ID、用户要减的钻石个数
     * @return
     * @author yoko
     * @date 2019/12/3 21:36
     */
    public int updateConsumerSubtractMasonry(ConsumerModel model);

    /**
     * @Description: 修改用户的钻石-解冻
     * @param model - 用户解冻数据
     * @return int
     * @author yoko
     * @date 2019/12/10 10:52
     */
    public int updateConsumerMasonryByThaw(ConsumerModel model);

    /**
     * @Description: 根据达人等级查询用户
     * @param darenLevel - 达人等级
     * @return List - 集合
     * @author yoko
     * @date 2019/12/23 17:05
     */
    public List<ConsumerModel> getConsumerByDarenLevel(int darenLevel);
}
