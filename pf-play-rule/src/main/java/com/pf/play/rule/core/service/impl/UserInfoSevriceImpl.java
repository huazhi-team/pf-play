package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.UserInfoMapper;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.service.UserInfoSevrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 21:10
 * @Version 1.0
 */
@Service
public class UserInfoSevriceImpl<T> extends BaseServiceImpl<T> implements UserInfoSevrice<T> {
    @Autowired
    private UserInfoMapper userInfoMapper ;

    @Override
    public BaseDao<T> getDao() {
        return userInfoMapper;
    }
    /**
     * @Description: 查询用户详细信息
     * @param type 1、微信  2 手机号+ 密码
    * @param wxOpenid
    * @param phone
    * @param passWord
     * @return com.pf.play.rule.core.model.UserInfoModel
     * @author long
     * @date 2019/11/13 21:47
     */
    @Override
    public UserInfoModel getUserInfo(Integer type,String wxOpenid,String phone,String passWord) {

        UserInfoModel  model = new UserInfoModel();
        if(type==1){
            UserInfoModel userInfoModel= new UserInfoModel();
            userInfoModel.setWxOpenid(wxOpenid);
            model = userInfoMapper.selectByUserInfo(userInfoModel);
        }
//        else if (type==2){//暂时没需求
//            UserInfoModel userInfoModel= new UserInfoModel();
//            userInfoModel.setPassword();
//            userInfoMapper.selectByUserInfo(userInfoModel);
//        }
        return model;
    }

    @Override
    public boolean receiveTaskReward(Integer type, String wxOpenid, String phone, String passWord) throws  Exception{

        //查询用户最高的任务要求，判断是否完成。

        //如果完成获取所有的任务id 进行用户的数据修改以及记录表进行添加

        //修改任务id 进行任务领取次数、天数 进行修改

        return false;
    }



}
