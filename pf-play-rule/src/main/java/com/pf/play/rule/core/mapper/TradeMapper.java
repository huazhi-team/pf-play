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
}
