package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 虚拟币每天兑换的价格的Dao层
 * @Author yoko
 * @Date 2019/11/21 22:17
 * @Version 1.0
 */
@Mapper
public interface VirtualCoinPriceMapper<T> extends BaseDao<T> {
}
