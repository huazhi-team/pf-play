package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 策略的Dao层
 * @Author yoko
 * @Date 2019/11/21 21:07
 * @Version 1.0
 */
@Mapper
public interface StrategyMapper<T> extends BaseDao<T> {
}
