package com.pf.play.rule.core.service;


import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.AipResultInfoModel;

/**
 * @author df
 * @Description:结果同步的Service层
 * @create 2019-05-29 14:45
 **/
public interface AipResultInfoService<T> extends BaseService<T> {

    /**
     * @Description: TODO(新增结果记录表_增值同步给渠道的)
     * @author df
     * @param model
     * @create 15:01 2019/5/29
     **/
    public void addResultInfoChannel(AipResultInfoModel model);

    /**
     * @Description: TODO(计算渠道针对代码的当日金额)
     * @author df
     * @param model
     * @create 16:45 2019/5/29
     **/
    public AipResultInfoModel countDayMoney(AipResultInfoModel model);

    /**
     * @Description: TODO(根据渠道透传参数值信息，并且缓存到redis中)
     * @author df
     * @param model
     * @create 17:32 2019/5/29
     **/
    public AipResultInfoModel getAipResultInfo(AipResultInfoModel model);


}
