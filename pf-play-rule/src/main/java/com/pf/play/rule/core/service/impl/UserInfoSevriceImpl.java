package com.pf.play.rule.core.service.impl;

import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.UserInfoSevrice;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;

    @Autowired
    private DisVitalityValueMapper disVitalityValueMapper;
    @Autowired
    private DisEmpiricalValueLevelMapper disEmpiricalValueLevelMapper;

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

    @Override
    public void updatePayPassword(UserInfoModel model){
        try {
            vcMemberMapper.updatePayPassword(model);
        }catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * @Description: 查询我上级信息
     * @param memberId
     * @return java.util.List<com.pf.play.rule.core.model.VcMember>
     * @author long
     * @date 2019/11/27 11:02
     */
    public VcMember getMySuperiorInfo(Integer memberId){
        VcMember  vcMember= TaskMethod.changvcMember(memberId);
        VcMember  vcMember1=TaskMethod.changvcMemberTOsuperiorId(memberId);
        VcMemberResource  vcMemberResource  =  ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(vcMember1.getMemberId());
        vcMember1.setTeamPeople(vcMemberResource.getTeamPeople());
        vcMember1.setDarenLevel(vcMember1.getDarenLevel());
        vcMember1.setEmpiricalLevel(vcMember1.getEmpiricalLevel());
        return   vcMember1;
    }

    /**
     * @Description: 我的直推信息
     * @param memberId
     * @return java.util.List<com.pf.play.rule.core.model.VcMember>
     * @author long
     * @date 2019/11/27 11:10
     */
    public List<VcMember> getMyUpInfo(Integer memberId){
        List<VcMember>   list = new ArrayList<VcMember>();
        VcMember  vcMember=TaskMethod.changvcMemberTOsuperiorId(memberId);
        List<VcMember>  list1 =  vcMemberMapper.selectByMemberId(vcMember);
        for(VcMember vcMember1 :list1){
            VcMemberResource  vcMemberResource  =  ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(vcMember1.getMemberId());
            vcMember1.setTeamPeople(vcMemberResource.getTeamPeople());
            vcMember1.setDarenLevel(vcMember1.getDarenLevel());
            vcMember1.setEmpiricalLevel(vcMember1.getEmpiricalLevel());
            list.add(vcMember1);
        }
        return   list;
    }

    /**
     * @Description: 查询用户资源详细信息
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/27 11:18
     */
    @Override
    public VcMemberResource getMyTeamResourceInfo(Integer memberId) {
        VcMemberResource  vcMemberResource  = TaskMethod.changeQueryMemberResource(memberId);
        VcMemberResource  vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        return vcMemberResource1;
    }

    @Override
    public List<Empirical> getMyEmpirical(Integer memberId) {
        List<DisEmpiricalValueLevel> list = disEmpiricalValueLevelMapper.selectByPrimaryKey();
        List<Empirical>    empiricalList  = MyMethod.toDisEmpiricalValue(list);
        return empiricalList;
    }

    @Override
    public List<Vitality> getMyDisVitalityValue(Integer memberId) {
        List<DisVitalityValue> list = disVitalityValueMapper.selectByPrimaryKey();
        List<Vitality>    vitalityList  = MyMethod.toDisVitalityValue(list);
        return vitalityList;
    }

    @Override
    public VcMember getMemeber(Integer memberId) {
        VcMember vcMember  = TaskMethod.changvcMember(memberId);
        VcMember vcMember1  = vcMemberMapper.selectByPrimaryKey(vcMember);
        return vcMember1;
    }

    @Override
    public boolean updateMemeber(VcMember vcMember) {
        int count  = vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        if(count != 0 ){
            return true;
        }
        return false;
    }


}
