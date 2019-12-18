package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 用户访问、异常的流水的Dao层
 * @Author yoko
 * @Date 2019/12/18 14:20
 * @Version 1.0
 */
@Mapper
public interface StreamConsumerMapper<T> extends BaseDao<T> {

    /**
     * @Description: 添加用户访问正常的流水数据
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/18 14:23
     */
    public int addVisit(StreamConsumerModel model);


    /**
     * @Description: 添加用户访问异常的流水数据
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/18 14:23
     */
    public int addError(StreamConsumerModel model);
}
