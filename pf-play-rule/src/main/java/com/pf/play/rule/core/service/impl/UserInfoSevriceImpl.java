package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.HttpGetUtil;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.appeal.RequestAppeal;
import com.pf.play.model.protocol.request.uesr.SynchronousReq;
import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.uesr.MyFriendsResp;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.CommonService;
import com.pf.play.rule.core.service.UserInfoSevrice;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
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
    private VcThirdPartyMapper vcThirdPartyMapper;



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
        VcMemberResource  vcMemberResource  =  ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(vcMember.getMemberId());
        if(vcMemberResource==null){
            vcMember1 =null;
            return vcMember1;
        }
        vcMember1.setInviteCode(vcMemberResource.getPhone());
        vcMember1.setNickname(vcMemberResource.getNickName());
        vcMember1.setIsCertification(vcMemberResource.getIsCertification());
        vcMember1.setTeamPeople(vcMemberResource.getTeamPeople());
        vcMember1.setDarenLevel(vcMemberResource.getDarenLevel());
        vcMember1.setEmpiricalLevel(vcMemberResource.getEmpiricalLevel());
        vcMember1.setCreateTimeStr(vcMemberResource.getCreateTimeStr());
        vcMember1.setUpdateTimeStr(vcMemberResource.getUqdateTimeStr());
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
        List<VcMember>  list1 =  vcMemberMapper.selectByPid(vcMember);
        for(VcMember vcMember1 :list1){
            VcMemberResource  vcMemberResource  =  ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(vcMember1.getMemberId());
            vcMember1.setTeamPeople(vcMemberResource.getTeamPeople());
            vcMember1.setDarenLevel(vcMemberResource.getDarenLevel());
            vcMember1.setEmpiricalLevel(vcMemberResource.getEmpiricalLevel());
            vcMember1.setCreateTimeStr(vcMemberResource.getCreateTimeStr());
            vcMember1.setUpdateTimeStr(vcMemberResource.getUqdateTimeStr());
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
        VcMember vcMemberParameter =  TaskMethod.changvcMember(memberId);
        VcMemberResource  vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        VcMember  vcMember   =vcMemberMapper.selectByPrimaryKey(vcMemberParameter);
        if(vcMemberResource1==null||vcMember==null){
            return  null;
        }
        vcMemberResource1.setNickName(vcMember.getNickname());
        vcMemberResource1.setIsCertification(vcMember.getIsCertification());
        vcMemberResource1.setPhone(vcMember.getInviteCode());
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


    /**
     * @Description: token 是否被使用
     * @param token
     * @return boolean
     * @author long
     * @date 2019/11/30 18:27
     */
    public boolean  isToken(String  token){
        boolean    flag =  false;
        VcThirdParty    vcThirdParty = new VcThirdParty();
        vcThirdParty.setToken(token);
        String tokenstr = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, token);
        String tokenExist  = (String)ComponentUtil.redisService.get(tokenstr);
        if(!StringUtils.isBlank(tokenExist)&&tokenExist.equals("1")){
            return     flag;
        }
        
        VcThirdParty  vcThirdParty1  =  vcThirdPartyMapper.selectByPrimaryKey(vcThirdParty);
        if(vcThirdParty1==null){
            flag = true;
        }
        return     flag ;
    }

    @Override
    public MyFriendsResp toMyFriensResp(Integer memberId, Integer superiorId)throws  ServiceException   {
        VcMember     vcMember    = ComponentUtil.userInfoSevrice.getMySuperiorInfo(superiorId);
        VcMemberResource vcMemberResource = ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(memberId);
        if(vcMember==null){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
        }
        if(vcMemberResource==null){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
        }
        List<VcMember>      vcMemberList  = ComponentUtil.userInfoSevrice.getMyUpInfo(memberId);
        MyFriendsResp   myFriendsResp = MyMethod.toMyFriendsResp(vcMember,vcMemberResource,vcMemberList);
        return  myFriendsResp;
    }

    @Override
    public Boolean userSynchronousQhr(Integer memberId,String token) {
        boolean  flag  = false ;
        try{
            VcMember  vcMember   =TaskMethod.getMember(memberId);
            VcMember  vcMember1  = vcMemberMapper.selectByPrimaryKey(vcMember);
            String   param = MyMethod.toSynchronousResp(vcMember1,token);
            String   rs = HttpGetUtil.sendPost(Constant.USER_SYNCHRONOUS_URL,param);
            SynchronousReq req =JSON.parseObject(rs, SynchronousReq.class);
            if(req !=null){
                if(req.getErrcode()==0){
                    flag=true;
                }
            }
        }catch (Exception  e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public void superiorSynchronousQhr(List<VcMember> list) {
        List<Integer> idList    =  new ArrayList<>();
        for(VcMember vcMember:list){
            String   param   =  SynchroMethod.toSyncPrarentId(vcMember);
            String   codeInfo =  HttpGetUtil.send(Constant.PRARENT_SYNCHRONOUS_URL,param);
            PrarentResult prarentResult  = JSON.parseObject(codeInfo, PrarentResult.class);
            if(!StringUtils.isBlank(prarentResult.getErrcode())&&prarentResult.getErrcode().equals("0")){
                idList.add(vcMember.getMemberId());
            }
        }
        VcMember vcMember = new VcMember();
        vcMember.setIdList(idList);
        if(idList.size()!=0){
            vcMemberMapper.updateMemberIdList(vcMember);
        }
    }


    @Override
    public void executeSuperior() {
        try{
            while (true){
                List<VcMember>   list =vcMemberMapper.selectByisSynchro(null);
                if(list.size()==0){
                    Thread.sleep(3000);
                }else{
                    ComponentUtil.userInfoSevrice.superiorSynchronousQhr(list);
                }
            }
        }catch (Exception re){
            re.printStackTrace();
        }

    }

    @Override
    public VcMemberResource getMyResourceInfo(Integer memberId) {
        VcMemberResource  vcMemberResource = TaskMethod.changvcMemberResource(memberId);
        return vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
    }
}
