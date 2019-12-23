package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.trade.TradeModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 交易的Dao层
 * @Author yoko
 * @Date 2019/11/26 22:40
 * @Version 1.0
 */
@Mapper
public interface TradeMapper<T> extends BaseDao<T> {

    /**
     * @Description: 计算当前买量、今日成交量
     * <p>
     *     当前买量的查询条件：orderStatus=1；
     *     今日成交量的查询条件：orderStatus=3 and curday = 今天
     * </p>
     * @param model - orderStatus、curday
     * @return String
     * @author yoko
     * @date 2019/12/2 14:45
     */
    public String getOrderTradeNum(TradeModel model);

    /**
     * @Description: 修改订单状态：交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
     * <p>
     *     买家确认付款时：会有图片值这个字段
     * </p>
     * @param model
     * @return boolean
     * @author yoko
     * @date 2019/12/3 11:31
     */
    public int updateTradeStatus(TradeModel model);

    /**
     * @Description: 更新订单交易流水的申诉状态
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/6 17:10
     */
    public int updateTradeAppealStatus(TradeModel model);


    /**
     * @Description: 更新订单交易流水超时的状态
     * @param model - 订单交易流水信息
     * @return int
     * @author yoko
     * @date 2019/12/10 10:05
     */
    public int updateTradeOverTime(TradeModel model);

    /**
     * @Description: 获取一天的订单交易手续费
     * SUM(service_charge)
     * @param curday - 日期
     * @return String
     * @author yoko
     * @date 2019/12/23 15:54
     */
    public String getOneDayServiceCharge(int curday);
}
