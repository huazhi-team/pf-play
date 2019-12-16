package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/20 10:31
 * @Version 1.0
 */
public interface InitService<T> extends BaseService<T> {
     void initTask();

     void initVitalityInfo();

     void initEmpiricalInfo();

     void initEmpiricalVitalityAttribute();

     void initSystemDictionary();
}
