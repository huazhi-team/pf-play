package com.pf.play.rule.core.service;

import com.pf.play.model.protocol.request.uesr.BaseReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 23:37
 * @Version 1.0
 */
@Service
public interface UserMasonryService <T> extends BaseService<T> {
    List<UMasonryListLog> queryMasonryListLog(Integer memberId);
    int     AddqueryMasonryListLog(UMasonryListLog uMasonryListLog);

    List<UMasonryListLog> toKenQueryMasonryInfo(Integer memberId)throws Exception;
    Integer  queryTokenMemberId(String  token,String wxOpenId);
    List<SysTypeDictionary>  getMasonryType();
    UserInfoModel queryTokenSuperiorId(String token, String wxOpenId);
    UMasonrySummary queryUMasonrySummary(Integer memberId);



}


