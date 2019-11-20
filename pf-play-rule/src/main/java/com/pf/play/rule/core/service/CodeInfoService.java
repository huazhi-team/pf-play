package com.pf.play.rule.core.service;


import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.CodeInfoModel;

/**
 * @author df
 * @Description:代码信息的Service层
 * @create 2019-05-22 15:31
 **/
public interface CodeInfoService<T> extends BaseService<T> {

    /**
     * @Description: TODO(根据ID获取代码信息，并且缓存到redis中)
     * @author df
     * @param model:根据id
     * @create 15:38 2019/5/22
     **/
    public CodeInfoModel getCodeInfo(CodeInfoModel model);
}
