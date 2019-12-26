package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 阿里支付的Dao层
 * @Author yoko
 * @Date 2019/12/26 13:51
 * @Version 1.0
 */
@Mapper
public interface AlipayMapper<T> extends BaseDao<T> {
}
