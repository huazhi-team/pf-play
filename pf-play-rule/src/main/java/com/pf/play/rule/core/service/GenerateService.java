package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 15:01
 * @Version 1.0
 */
public interface GenerateService<T> extends BaseService<T> {
    public String  getNonexistentInformation(int type)throws  Exception;

    public Integer  getMemberId()throws  Exception;
}
