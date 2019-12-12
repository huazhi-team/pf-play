package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.uesr.BaseReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.mapper.UMasonryListLogMapper;
import com.pf.play.rule.core.mapper.UserInfoMapper;
import com.pf.play.rule.core.mapper.VcMemberMapper;
import com.pf.play.rule.core.model.Enum.MasonryTypeEnum;
import com.pf.play.rule.core.model.SysTypeDictionary;
import com.pf.play.rule.core.model.UMasonryListLog;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.service.UserInfoSevrice;
import com.pf.play.rule.core.service.UserMasonryService;
import com.pf.play.rule.core.singleton.RegisterSingleton;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 23:38
 * @Version 1.0
 */
@Service
public class UserMasonryServiceImpl<T> extends BaseServiceImpl<T> implements UserMasonryService<T> {

    @Autowired
    private UMasonryListLogMapper  uMasonryListLogMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Override
    public BaseDao<T> getDao() {
        return uMasonryListLogMapper;
    }
    /**
     * @Description: 根据用户查询砖石明细数据！
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/11/13 23:49
     */
    @Override
    public List<UMasonryListLog> queryMasonryListLog(Integer memberId) {
        UMasonryListLog uMasonryListLog = new UMasonryListLog();
        uMasonryListLog.setMemberId(memberId);
        List<UMasonryListLog> list = uMasonryListLogMapper.selectByInfo(uMasonryListLog);
        List<SysTypeDictionary> masonryTypelist =ComponentUtil.userMasonryService.getMasonryType();
//        if(masonryTypelist){
//
//        }
        if(masonryTypelist==null || masonryTypelist.size()==0){

        }else{
            for(UMasonryListLog uMasonryListLog1:list){
                for(SysTypeDictionary sysTypeDictionary:masonryTypelist){
                    if(uMasonryListLog1.getType()==Integer.parseInt(sysTypeDictionary.getValue())){
                        uMasonryListLog1.setTypeValue(sysTypeDictionary.getName());
                    }
                }
            }
        }

        return list;
    }
    /**
     * @Description: 根据用户的类型进行添加砖石记录表
     * @param uMasonryListLog
     * @return int
     * @author long
     * @date 2019/11/13 23:52
     */
    @Override
    public int AddqueryMasonryListLog(UMasonryListLog uMasonryListLog) {
        uMasonryListLogMapper.insertSelective(uMasonryListLog);
        return uMasonryListLogMapper.insertSelective(uMasonryListLog);
    }

    /**
     * @Description: 我的资产信息
     * @param loginReq
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/11/14 13:52
     */
    @Override
    public List<UMasonryListLog> toKenQueryMasonryInfo(Integer memberId)throws  Exception {

        if(memberId==0){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.USERMASONRY_ERRPR0.geteCode(),
                                            ErrorCode.ENUM_ERROR.USERMASONRY_ERRPR0.geteDesc());
        }
        List<UMasonryListLog> list= ComponentUtil.userMasonryService.queryMasonryListLog(memberId);
        return list;
    }

    /**
     * @Description: 判断token是否有效
     * @param token
    * @param wxOpenId
     * @return Integer
     * @author long
     * @date 2019/11/14 11:27
     */
    @Override
    public Integer queryTokenMemberId(String token,String wxOpenId) {
        UserInfoModel  model =null;
        try{
            String tokens = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, token);
            String rsTokens =(String) ComponentUtil.redisService.get(tokens);
            if(StringUtils.isBlank(rsTokens)||!rsTokens.equals("1")){
                return  0 ;
            }

            UserInfoModel  userInfoModel = new  UserInfoModel();
            userInfoModel.setToken(token);
            userInfoModel.setWxOpenid(wxOpenId);
            model  = userInfoMapper.select(userInfoModel);
            if(null == model||model.getMemberId()==0){
                return  0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return model.getMemberId();
    }



    @Override
    public UserInfoModel queryTokenSuperiorId(String token,String wxOpenId) {
        UserInfoModel  model =null;
        try{
            String tokens = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, token);
            String rsTokens =(String) ComponentUtil.redisService.get(tokens);
            if(rsTokens==null||!rsTokens.equals("1")){
                return  null ;
            }

            UserInfoModel  userInfoModel = new  UserInfoModel();
            userInfoModel.setToken(token);
            userInfoModel.setWxOpenid(wxOpenId);
            model  = userInfoMapper.select(userInfoModel);
            if(null == model||model.getMemberId()==0){
                return  null ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return model;
    }

    /**
     * @Description: 获取有效的砖石类型
     * @param
     * @return java.util.List<com.pf.play.rule.core.model.SysTypeDictionary>
     * @author long
     * @date 2019/11/17 15:00
     */
    @Override
    public List<SysTypeDictionary> getMasonryType() {
        List<SysTypeDictionary> masonryTypelist = RegisterSingleton.getInstance().getMasonryType();
        if(masonryTypelist==null||masonryTypelist.size()==0){
            ComponentUtil.commonService.quertCommenType();
            masonryTypelist = RegisterSingleton.getInstance().getMasonryType();
        }
        return masonryTypelist;
    }




}
