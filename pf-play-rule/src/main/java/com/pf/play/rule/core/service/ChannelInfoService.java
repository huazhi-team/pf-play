package com.pf.play.rule.core.service;


import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.ChannelInfoModel;

/**
 * @author df
 * @Description:渠道信息的Service层
 * @create 2019-05-27 16:44
 **/
public interface ChannelInfoService <T> extends BaseService<T> {

    /**
     * @Description: TODO(根据ID获取渠道信息，并且缓存到redis中)
     * @author df
     * @param model:根据id
     * @create 15:38 2019/5/22
     **/
    public ChannelInfoModel getChannelInfo(ChannelInfoModel model);
}
