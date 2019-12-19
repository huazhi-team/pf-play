package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.mapper.UserInfoMapper;
import com.pf.play.rule.core.mapper.VcThirdPartyMapper;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcThirdParty;
import com.pf.play.rule.core.service.LoginService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 20:32
 * @Version 1.0
 */
@Service
public class LoginServiceImpl<T> extends BaseServiceImpl<T> implements LoginService<T> {
    @Autowired
    private UserInfoMapper userInfoMapper ;
    @Autowired
    private VcThirdPartyMapper vcThirdPartyMapper ;

    @Override
    public BaseDao<T> getDao() {
        return userInfoMapper;
    }

    @Override
    public UserInfoResp login(LoginReq loginReq) throws  Exception{
        UserInfoResp userInfoResp =new UserInfoResp();
        /************参数是否正确*************/
        boolean  flag = ComponentUtil.loginService.checkDateNormal(loginReq);
        if(!flag){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.U000000.geteCode(),ErrorCode.ENUM_ERROR.U000001.geteDesc());
        }
        VcThirdParty  vcThirdParty1  =  LoginMethod.changvxOpenId(loginReq.getWxOpenId());
        vcThirdParty1 = vcThirdPartyMapper.selectByPrimaryKey(vcThirdParty1);//该用户是否注册

        if(vcThirdParty1==null){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.U000000.geteCode(),ErrorCode.ENUM_ERROR.U000000.geteDesc());
        }

        ComponentUtil.redisService.remove(vcThirdParty1.getToken()); //删除该用户直接的缓存token

        VcThirdParty  vcThirdParty  = LoginMethod.changVcThirdParty(loginReq.getWxOpenId(),LoginMethod.getToken(vcThirdParty1.getMemberId()));


        vcThirdPartyMapper.updateByWxOpenId(vcThirdParty);

        /**************是否有用户信息**************/
        UserInfoModel userInfoModel = ComponentUtil.userInfoSevrice.getUserInfo(loginReq.getLoginType(),loginReq.getWxOpenId(),
                loginReq.getPhone(),loginReq.getPassword());

        if (StringUtil.isEmpty(userInfoModel.getToken())){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.U000002.geteCode(),ErrorCode.ENUM_ERROR.U000002.geteDesc());
        }
        BeanUtils.copy(userInfoModel,userInfoResp);
        ComponentUtil.userInfoSevrice.userSynchronousQhr(userInfoModel.getMemberId(),userInfoModel.getToken());

        return userInfoResp;
    }

    /**
     * @Description: 检查是否有有效的数据！
     * @param loginReq
     * @return boolean
     * @author long
     * @date 2019/11/13 21:00
     */
    @Override
    public boolean checkDateNormal(LoginReq loginReq) {
        if(loginReq.getLoginType()==1){
            if(StringUtils.isBlank(loginReq.getWxOpenId())){
                return false;
            }
        }else if(loginReq.getLoginType()==1){
            if(StringUtils.isBlank(loginReq.getPassword())||StringUtils.isBlank(loginReq.getPhone())){
                return false;
            }
        }
        return true;
    }


    @Override
    public void signOut(VcThirdParty   vcThirdParty) {
        vcThirdPartyMapper.updateByWxOpenId(vcThirdParty);
    }
}
