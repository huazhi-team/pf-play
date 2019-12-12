package com.pf.play.rule;

import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.mapper.VcThirdPartyMapper;
import com.pf.play.rule.core.model.VcThirdParty;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/30 17:50
 * @Version 1.0
 */
public class LoginMethod {
    /**
     * @Description: 转换有效的登录数据
     * @param loginReq
     * @return com.pf.play.model.protocol.request.uesr.LoginReq  为空无效
     * @author long
     * @date 2019/11/30 18:01
     */
    public  static LoginReq changLoginReq(LoginReq loginReq){
        LoginReq   loginReq1   = new LoginReq();
        if(loginReq.getLoginType()==1){
            if(!StringUtils.isBlank(loginReq.getWxOpenId())){
                loginReq1.setWxOpenId(loginReq.getWxOpenId());
            }
        }
        return   loginReq1;
    }

    /**
     * @Description: 获取有效的token
     * @param
     * @return java.lang.String
     * @author long
     * @date 2019/11/30 19:11
     */
    public  static String  getToken()throws  Exception{
        String  token  = "" ;
        token= ComponentUtil.generateService.getNonexistentInformation(Constant.TOKEN);
        boolean  flag  = false ;
        flag  = ComponentUtil.userInfoSevrice.isToken(token);
        while (!flag){
            token= ComponentUtil.generateService.getNonexistentInformation(Constant.TOKEN);
            flag  = ComponentUtil.userInfoSevrice.isToken(token);
        }

        String tokenstr = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, token);
        ComponentUtil.redisService.onlyData(tokenstr, "1");
        return  token;
    }

    /**
     * @Description: 生成修改的类
     * @param wxOpenid
     * @param token
     * @return com.pf.play.rule.core.model.VcThirdParty
     * @author long
     * @date 2019/11/30 20:49
     */
    public  static VcThirdParty   changVcThirdParty(String  wxOpenid,String  token){
        VcThirdParty  vcThirdParty  = new VcThirdParty();
        vcThirdParty.setWxOpenid(wxOpenid);
        vcThirdParty.setToken(token);
        return   vcThirdParty;
    }

    /**
     * @Description: 登录后返回信息
     * @param userInfoResp
     * @return com.pf.play.model.protocol.response.uesr.LoginResp
     * @author long
     * @date 2019/11/30 21:00
     */
    public  static LoginResp changLoginResp(UserInfoResp userInfoResp){
        LoginResp  loginResp = new LoginResp();
        loginResp.setToken(userInfoResp.getToken());
        loginResp.setMemberAdd(userInfoResp.getMemberAdd());
        loginResp.setBirthday(userInfoResp.getBirthday());
        loginResp.setSex(userInfoResp.getSex());
        loginResp.setNickname(userInfoResp.getNickname());
        loginResp.setCity(userInfoResp.getCity());
        loginResp.setProvince(userInfoResp.getProvince());
        loginResp.setMemberId(userInfoResp.getMemberId());
        return  loginResp;
    }

    /**
     * @Description: 判断是否有token 删除缓存的token
     * @param loginReq
     * @return boolean  true 是删除成功   false 没找到
     * @author long
     * @date 2019/11/30 23:07
     */
    public static boolean  checkRemoveSignOutToken(LoginReq loginReq){
        boolean   flag = true ;
        if(StringUtils.isBlank(loginReq.getWxOpenId())){
            return  false ;
        }
        flag   = ComponentUtil.userInfoSevrice.isToken(loginReq.getToken());
        if(!flag){
            String tokenstr = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, loginReq.getToken());
            ComponentUtil.redisService.remove(tokenstr);
        }else{
            return  false ;
        }
        return    flag;
    }

    public static VcThirdParty  changLoginReqToVcThirdParty(UserCommonReq updateUserReq){
        VcThirdParty vcThirdParty = new VcThirdParty();
        vcThirdParty.setTokenExpire(0);
        vcThirdParty.setToken("");
        vcThirdParty.setWxOpenid(updateUserReq.getWxOpenId());
        return vcThirdParty;
    }

    /**
     * @Description: 根据openId 查询
     * @param wxOpenid
     * @return com.pf.play.rule.core.model.VcThirdParty
     * @author long
     * @date 2019/12/12 17:02
     */
    public  static VcThirdParty   changvxOpenId(String  wxOpenid){
        VcThirdParty  vcThirdParty  = new VcThirdParty();
        vcThirdParty.setWxOpenid(wxOpenid);
        return   vcThirdParty;
    }

}
