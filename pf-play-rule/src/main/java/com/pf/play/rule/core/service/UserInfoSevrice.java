package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.UserInfoModel;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/11 14:34
 * @Version 1.0
 */

public interface UserInfoSevrice  <T> extends BaseService<T> {
    public UserInfoModel getUserInfo(Integer type,String wxOpenid,String phone,String passWord);
    boolean  receiveTaskReward(Integer type,String wxOpenid,String phone,String passWord)throws  Exception;
}
