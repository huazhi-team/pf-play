package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 订单流水的Dao层
 * @Author yoko
 * @Date 2019/11/23 17:19
 * @Version 1.0
 */
@Mapper
public interface OrderMapper<T> extends BaseDao<T> {
}
