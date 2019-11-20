package com.pf.play.rule.core.service;

import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.response.task.uesr.UserInfoResp;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.BaseService;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 20:31
 * @Version 1.0
 */
public interface LoginService<T> extends BaseService<T> {
    UserInfoResp login(LoginReq loginReq) throws Exception ;
    boolean   checkDateNormal(LoginReq loginReq);

}
