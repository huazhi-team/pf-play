package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 订单交易流水的Task的Dao层
 * @Author yoko
 * @Date 2019/12/6 21:42
 * @Version 1.0
 */
@Mapper
public interface TaskOrderTradeMapper<T> extends BaseDao<T> {
}
