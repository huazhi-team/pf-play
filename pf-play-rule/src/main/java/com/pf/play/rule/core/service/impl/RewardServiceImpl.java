package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.mapper.VcMemberMapper;
import com.pf.play.rule.core.mapper.VcRealNameStayHandleMapper;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcRealNameStayHandle;
import com.pf.play.rule.core.service.RewardService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/14 18:09
 * @Version 1.0
 */
@Service
public class RewardServiceImpl<T> extends BaseServiceImpl<T> implements RewardService<T> {
    @Autowired
    private VcRealNameStayHandleMapper vcRealNameStayHandleMapper;

    @Autowired
    private VcMemberMapper vcMemberMapper ;

    @Override
    public BaseDao<T> getDao() {
        return vcMemberMapper;
    }

    @Override
    public void newUserSuperiorReward(Integer member) {

    }

    @Override
    public void realNameReward(Integer member) {
        //查询上级member

        //查询不大上级id  添加到异常表

        //查询得到上级id 进行数据修改

        //更新直推用户 以及上级所有的经验值都需要修改

        //更新团队人数

        //上级经验值加
    }

    @Override
    public void userExchangeReward() {

    }

    @Override
    public void downLevelReceiveTask() {

    }





    public  boolean  updateMemberInfo(VcMember vcMember)throws  Exception{
        boolean  flag  = false  ;
        VcMember info  =  vcMemberMapper.selectByPrimaryKey(vcMember);
        if(null==info||info.getExtensionMemberId().equals("")){
            //添加一个异常数据
            throw  new ServiceException(ErrorCode.ENUM_ERROR.REALNAME_ERRPR0.geteCode(),
                    ErrorCode.ENUM_ERROR.REALNAME_ERRPR0.geteDesc());
        }
        flag = ComponentUtil.rewardService.insertStayHanld(vcMember.getMemberId(),info);
        if(flag){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.REALNAME_ERRPR1.geteCode(),
                    ErrorCode.ENUM_ERROR.REALNAME_ERRPR1.geteDesc());
        }

        return   flag  ;
    }



    /**
     * @Description: 给用户信息插入奖励代处理表
     * @param memberId
    * @param info
     * @return boolean
     * @author long
     * @date 2019/11/15 15:49
     */
    @Override
    public  boolean    insertStayHanld(Integer memberId,VcMember info){
        boolean  flag = true ;
        Date date  = new Date();
        VcRealNameStayHandle   vcRealNameStayHandle = new VcRealNameStayHandle();
        vcRealNameStayHandle.setCreateMemberId(memberId);
        vcRealNameStayHandle.setHandleMemberId(info.getExtensionMemberId());
        vcRealNameStayHandle.setHandleTeamNumber(info.getExtensionMemberId());
        vcRealNameStayHandle.setCurday(DateUtil.getDayNumber(date));
        vcRealNameStayHandle.setCurhour(DateUtil.getHour(date));
        vcRealNameStayHandle.setCurminute(DateUtil.getCurminute(date));
        vcRealNameStayHandle.setUpdateTime(date);
        vcRealNameStayHandle.setCreateTime(date);
        int   count  = vcRealNameStayHandleMapper.insertSelective(vcRealNameStayHandle);
        if(count!=1){
            return  false;
        }
        return   flag;
    }

    @Override
    public boolean updateEmpiricalValue(Integer type, Integer memberId, String proMemberId, Float empiricvValue) {
        return false;
    }
}
