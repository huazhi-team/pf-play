package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 订单违约纪录的Dao层
 * @Author yoko
 * @Date 2019/12/9 17:28
 * @Version 1.0
 */
@Mapper
public interface OrderViolateMapper <T> extends BaseDao<T> {
}
