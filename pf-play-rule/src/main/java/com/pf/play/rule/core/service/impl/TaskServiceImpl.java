package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.*;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.TaskService;
import com.pf.play.rule.core.singleton.RegisterSingleton;
import com.pf.play.rule.core.singleton.TaskSingleton;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 15:14
 * @Version 1.0
 */
@Service
public class TaskServiceImpl<T> extends BaseServiceImpl<T> implements TaskService<T> {
    private final static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    private UserInfoMapper   userInfoMapper;

    @Autowired
    private UTaskHaveMapper uTaskHaveMapper;

    @Autowired
    private DisTaskAttributeMapper disTaskAttributeMapper;

    @Autowired
    private DisTaskTypeMapper disTaskTypeMapper;

    @Autowired
    private UdailyTaskStatMapper udailyTaskStatMapper;

    @Autowired
    private UDayTaskRewardMapper uDayTaskRewardMapper;

    @Autowired
    private VcRewardReceiveMapper  vcRewardReceiveMapper;


    @Autowired
    private VcMemberResourceMapper  vcMemberResourceMapper;

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private UvitalityValueListMapper uvitalityValueListMapper;

    @Autowired
    private DisWisemanInfoMapper disWisemanInfoMapper;

    @Autowired
    private UMasonryListLogMapper uMasonryListLogMapper;

    @Autowired
    private USubRewardMapper uSubRewardMapper;






    @Override
    public BaseDao<T> getDao() {
        return userInfoMapper;
    }

    /**
     * @Description: 用户的任务表
     * @param memberId
     * @return java.util.List<com.pf.play.rule.core.model.UTaskHave>
     * @author long
     * @date 2019/11/19 20:54
     */
    @Override
    public List<UTaskHave> getMyTask(Integer  memberId) {
        UTaskHave record   =  new  UTaskHave();
        record.setMemberId(memberId);
        record.setCurrentState(1);
        List<UTaskHave>  list = uTaskHaveMapper.selectByPrimaryKey(record);
        return list;
    }

    @Override
    public int typeToReceiveTask(String  token ,Integer member_id, Integer type) {
        List<SysTypeDictionary>    taskTypeList = RegisterSingleton.getInstance().getTaskType();
        //信息准确性功能

        //验证是否自有条件进行购买


        //
        for(SysTypeDictionary sysTypeDictionary:taskTypeList){
            if(sysTypeDictionary.getValue().equals("1")){
                UserInfoModel  userInfoModel=  new UserInfoModel();
                UserInfoModel  userList = userInfoMapper.selectByUserInfo(userInfoModel);
                break;
            }else if(sysTypeDictionary.getValue().equals("2")){
                break;
            }else if(sysTypeDictionary.getValue().equals("3")){
                break;
            }else if(sysTypeDictionary.getValue().equals("4")){
                break;
            }else if(sysTypeDictionary.getValue().equals("5")){
                break;
            }else if(sysTypeDictionary.getValue().equals("6")){
                break;
            }else if(sysTypeDictionary.getValue().equals("7")){
                break;
            }
        }
        return 0;
    }

    /**
     * @Description: 用户过期任务详细信息
     * @param member_id
     * @return java.util.List<com.pf.play.rule.core.model.UTaskHave>
     * @author long
     * @date 2019/11/19 21:07
     */
    @Override
    public List<UTaskHave> queryInvalidHaveTask(Integer member_id) throws Exception {
        UTaskHave record  = new UTaskHave();
        record.setMemberId(member_id);
        record.setCurrentState(1);
        List<UTaskHave> list = uTaskHaveMapper.selectNoValidTask(record);
        return list;
    }
    /**
     * @Description: 检查最大的是否有效
     * @param list
     * @return boolean
     * @author long
     * @date 2019/11/18 22:36
     */
    @Override
    public boolean checkSatisfyMaxTask(List<UTaskHave> list) {
        if(list.size()==0){
            return  false;
        }
        UTaskHave  model = list.get(0);
        DisTaskType   disTaskType   =    new DisTaskType();
        disTaskType.setTaskId(model.getTaskId());
        List<DisTaskType>  disTaskType1 =disTaskTypeMapper.selectByPrimaryKey(disTaskType);
        DisTaskAttribute   disTaskAttribute = new  DisTaskAttribute();
        disTaskAttribute.setTaskId(model.getTaskId());

        String  startTime = model.getStartTime()+"000";
        Long  day =DateUtil.timeDifference(Long.parseLong(startTime));
        if(disTaskType1.size()==0){
            return  false;
        }

        List<DisTaskAttribute>    disAttributeList = disTaskAttributeMapper.selectByPrimaryKey(disTaskAttribute);
        if(disAttributeList.size()==0){
            return  false;
        }

        if(model.getSurplusNum()==0){//任务次数到了
            return  false;
        }else if(disTaskType1.get(0).getTaskValidityDay()<=day){//任务周期到了
            return  false;
        }

        UdailyTaskStat record  =  new UdailyTaskStat();
        record.setCurday(DateUtil.getDayNumber(new Date()));
        record.setMemberId(model.getMemberId());
        UdailyTaskStat  statModel = udailyTaskStatMapper.selectByPrimaryKey(record);  //每天用户名明细信息
        if(statModel==null){
            return  false;
        }

        if(Integer.getInteger(disAttributeList.get(0).getKey1())>statModel.getAcceptNumber()){//点赞次数
            return  false;
        }
        if(Integer.getInteger(disAttributeList.get(0).getKey2())>statModel.getLookCommodityNum()){//查看商品次数
            return  false;
        }
        return true;
    }

    @Override
    public void taskReward(List<UTaskHave> list, Integer memberId) throws Exception {
        Date date  = new Date();
        for(UTaskHave uTaskHave:list){
            UDayTaskReward record =  new UDayTaskReward();
            record.setMemberId(memberId);
            record.setTaskId(uTaskHave.getTaskId());
            record.setCurday(DateUtil.getDayNumber(date));
            record.setCurhour(DateUtil.getHour(date));
            record.setCurminute(DateUtil.getCurminute(date));
            ComponentUtil.transactionalService.updateTask(record,uTaskHave);
        }

    }

    /**
     * @Description: 用户注册获取初级任务奖励
     * @param memberId
     * @return void
     * @author long
     * @date 2019/11/19 16:34
     */
    @Override
    public void userRegisterToTask(Integer memberId) throws Exception {
        DisTaskType    disTaskType = new DisTaskType();
        disTaskType.setTaskId(1);
        List<DisTaskType>  taskList  = TaskSingleton.getInstance().getDisTaskTypeList();

        if(taskList.size()==0){
            return;
        }

        Date   date  = new Date();
        Date endTime = DateUtil.getDateBetween(date,taskList.get(0).getTaskValidityDay());
        UTaskHave   uTaskHave =new  UTaskHave();
        uTaskHave.setMemberId(memberId);
        uTaskHave.setTaskId(1);
        uTaskHave.setSurplusDay(taskList.get(0).getTaskValidityDay());
        uTaskHave.setStartTime(date);
        uTaskHave.setEndTime(endTime);
        uTaskHave.setSurplusNum(taskList.get(0).getTotalNum());
        uTaskHave.setCurday(DateUtil.getDayNumber(date));
        uTaskHave.setCurhour(DateUtil.getHour(date));
        uTaskHave.setCurminute(DateUtil.getCurminute(date));
        uTaskHave.setGiveType(1);
        uTaskHave.setTaskLevel(1);
        uTaskHave.setCreateTime(date);
        uTaskHave.setUpdateTime(date);
        uTaskHave.setEveryNum(taskList.get(0).getEveryNum());
        uTaskHave.setSurplusCount(taskList.get(0).getTaskCircleDay());
        uTaskHaveMapper.insertSelective(uTaskHave);
        VcRewardReceive  vcRewardReceive = new VcRewardReceive();
        vcRewardReceive.setMemberId(memberId);
        vcRewardReceive.setIsLevel0(2);
        vcRewardReceiveMapper.updateByPrimaryKeySelective(vcRewardReceive);

//        uTaskHaveMapper.insertSelective();
    }

    /**
     * @Description: 查询还有这用户的领取任务情况，包括自己拥有多少任务
     * @param memberid
     * @return java.util.List<com.pf.play.rule.core.model.DisTaskType>
     * @author long
     * @date 2019/11/19 20:35
     */
    @Override
    public List<DisTaskType> queryReceiveTask(Integer memberid) {
        List<DisTaskType>  list  = TaskSingleton.getInstance().getDisTaskTypeList();
        List<UTaskHave>  haveList =uTaskHaveMapper.selectValidTask(memberid);//拥有的id
        VcMemberResource vc =TaskMethod.changvcMemberResource(memberid);
        VcMemberResource rsVc =vcMemberResourceMapper.selectByPrimaryKey(vc);
        if(rsVc==null){
            return  list;
        }

        List<DisTaskAttribute>  listDisTaskAttribute  = TaskSingleton.getInstance().getAttributeTypeList2();
        for(DisTaskType disTaskType1:list){
//            if(disTaskType1.getTaskId()==1){
//                list.remove(disTaskType1);
//                continue;
//            }
            disTaskType1.setMyCharmValue(rsVc.getCharmValue());
            List<DisTaskAttribute>  attributeList = TaskSingleton.getInstance().getAttributeTypeList1();
            for(DisTaskAttribute disTaskAttribute1:attributeList){
                if(disTaskAttribute1.getTaskId()==disTaskType1.getTaskId()){
                    disTaskType1.setWhere1(disTaskAttribute1.getKey1());
                    disTaskType1.setWhere2(disTaskAttribute1.getKey2());
                    break;
                }
            }

            if(haveList.size()==0){
                disTaskType1.setHavaCount(0);
            }else{
                for(UTaskHave uTaskHave1:haveList){
                    if(uTaskHave1.getTaskId()==disTaskType1.getTaskId()){
                        disTaskType1.setHavaCount(uTaskHave1.getTaskCount());
                        break;
                    }else{
                        disTaskType1.setHavaCount(0);
                    }
                }
            }

            for (DisTaskAttribute disTaskAttribute:listDisTaskAttribute){
                if(disTaskAttribute.getTaskId()==disTaskType1.getTaskId()){
                    disTaskType1.setActiveValue(disTaskAttribute.getKey1());
                    break;
                }
            }
        }


        return list;
    }


    @Override
    public boolean checkUserCondition(Integer memberId, Integer taskId)throws Exception {
        //第一步check 用户金额以及taskId 是否有效
        boolean  flag = false ;
        if(taskId==1){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR10.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR10.geteDesc());
        }

        List<DisTaskType>  taskList = TaskSingleton.getInstance().getDisTaskTypeList();

        DisTaskType  disTaskType1 =new DisTaskType();
        for(DisTaskType disTaskType:taskList){
            if(disTaskType.getTaskId()==taskId){
                BeanUtils.copy(disTaskType,disTaskType1);
                flag =  true;
                break;
            }
        }

        if(!flag){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
        }

        //查询任务数是否到了


        //用户是否有足够的金额

        VcMemberResource record  = TaskMethod.changeQueryMemberResource(memberId);
        VcMemberResource   vcMemberResource  =  vcMemberResourceMapper.selectByPrimaryKey(record);
        VcMember  vcMember  =  vcMemberMapper.selectByPrimaryKey(TaskMethod.getMember(memberId));

        if(!TaskMethod.isCertification(vcMember)){//是否是实名制用户
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR0.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR0.geteDesc());
        }

        if(vcMemberResource==null){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR2.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR2.geteDesc());
        }
        //用户金额是否足够购买任务
        if(vcMemberResource.getDayMasonry()<disTaskType1.getNeedResource()){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR3.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR3.geteDesc());
        }

        int   count  =0 ;
        List<UTaskHave>  list = uTaskHaveMapper.selectByPrimaryKey(TaskMethod.getuTaskHave(memberId));
        for(UTaskHave uTaskHave1 :list){
            if(taskId==uTaskHave1.getTaskId()){
                count++;
            }
        }

        //持有数是否满足
        if(count>=disTaskType1.getHoldNumber()){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR4.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR4.geteDesc());
        }

        return true;
    }

    @Override
    public boolean addUserTask(Integer memberId, Integer taskId)throws Exception {
        DisTaskType   taskType= TaskMethod.changeDisTaskType(taskId);
        if(taskType==null){
            return false;
        }

        UTaskHave   uTaskHave = TaskMethod.changeAddUTaskHave(memberId,taskId,taskType.getTaskValidityDay(),taskType.getTotalNum(),
                        taskType.getTaskLevel(),taskType.getTaskCircleDay(),taskType.getEveryNum());


        DisTaskAttribute disTaskAttribute = TaskMethod.taskIdChangeDisTaskAttribute(taskId);

        VcMemberResource  vcMemberResource  =new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        VcMember record =new VcMember();
        record.setMemberId(memberId);

        //该任务需要的魅力值
        Double charmValue =  TaskMethod.getNeedCharmValue(taskId);

        VcMemberResource  resourceRs = vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        VcMember record1 = vcMemberMapper.selectByPrimaryKey(record);
        Double  masonry=resourceRs.getDayMasonry()-taskType.getNeedResource() ;
        Double  myActiveValue=Double.valueOf(disTaskAttribute.getKey1());
        Double  upActiveValue=Double.valueOf(disTaskAttribute.getKey2());

        if(masonry<0){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR5.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR5.geteDesc());
        }

        //用户砖石明细表添加一条记录
        UMasonryListLog   uMasonryListLog = TaskMethod.changeUMasonryListLog(memberId,taskId, Constant.TASK_TYPE9,Constant.TASK_SYMBOL_TYPE2,Double.valueOf(taskType.getNeedResource()));
        UvitalityValueList myUvitalityValueList = TaskMethod.pottingVitalityValue(memberId,Constant.ACTIVE_TYPE2,Constant.TASK_SYMBOL_TYPE1,myActiveValue);
        UvitalityValueList uqUvitalityValueList = TaskMethod.pottingVitalityValue(record1.getSuperiorId(),Constant.ACTIVE_TYPE5,Constant.TASK_SYMBOL_TYPE1,upActiveValue);
        VcMemberResource insertResource = TaskMethod.changeUpdateResource(memberId,masonry,charmValue);
        UMasonrySummary uMasonrySummary = TaskMethod.updateUMasonrySummary(memberId,Constant.TASK_SYMBOL_TYPE2,masonry);

        UEmpiricalValueList    empiricalValueList=MyMethod.insertUEmpiricalValueList(memberId,memberId,taskId,taskType.getNeedResource());
        VcMemberResource uqEmpirical =TaskMethod.changSuperiorId(resourceRs,taskType.getNeedResource());
        try{
            ComponentUtil.transactionalService.buyTaskUpdateInfo(uTaskHave,insertResource,uMasonryListLog,myUvitalityValueList,uqUvitalityValueList,uMasonrySummary,uqEmpirical,empiricalValueList);
        }catch (Exception e){
            e.printStackTrace();
            throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR6.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR6.geteDesc());
        }
        return true;
    }

    @Override
    public boolean addUserGrantReward(Integer memberId, Integer taskId)throws Exception {
        return false;
    }

    /**
     * @Description: 用户未处理的活力值明细，更新用户明细表。
     * @param
     * @return void
     * @author long
     * @date 2019/11/21 13:49
     */
    @Override
    public void updateUserVitalityValue(UvitalityValueList  uvitalityValue ) {
        List<UvitalityValueList>  successObject=new ArrayList<>();
        List<UvitalityValueList>  failObject=new ArrayList<>();

        //获取该用户的上级信息
        VcMember  vcMember  =  vcMemberMapper.selectByMemberId(TaskMethod.changvcMember(uvitalityValue.getMemberId()));

        if(vcMember!=null){

        }

        VcMemberResource vcMemberResource1 = vcMemberResourceMapper.selectByPrimaryKey(TaskMethod.changeQueryMemberResource(uvitalityValue.getMemberId()));
        if(vcMemberResource1==null){
            failObject.add(uvitalityValue);
        }else{
            int stat = ComponentUtil.taskService.updateUserInfoToVitalityValue(vcMemberResource1,uvitalityValue);
            if(stat==0){
                failObject.add(uvitalityValue);
            }else{
                successObject.add(uvitalityValue);
            }
        }

       // TaskMethod.exeUserVitalityValueType(uvitalityValueList,successObject,failObject);

    }

    @Override
    public void updateUserVitalityValueType(UvitalityValueList uvitalityValueList, Integer type) {
        if(uvitalityValueList==null){
            return;
        }
        uvitalityValueList.setIsCount(type);
        UvitalityValueList  bean  =  TaskMethod.toUqdateInfo(uvitalityValueList);
        uvitalityValueListMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public int updateUserInfoToVitalityValue(VcMemberResource vcMemberResource,UvitalityValueList uvitalityValueList) {
        if(vcMemberResource==null||uvitalityValueList==null){
            return  0;
        }
        Double  updateValue=null;
        if(uvitalityValueList.getSymbolType()==1){
            updateValue=vcMemberResource.getActiveValue()+uvitalityValueList.getActiveValue();
        }else{
            updateValue=vcMemberResource.getActiveValue()-uvitalityValueList.getActiveValue();
        }
        if(updateValue>=0){
            VcMemberResource vcMemberResource1  = TaskMethod.changUpdateResourceTOActiveValue(vcMemberResource.getMemberId(),updateValue);
            vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource1);
            return 1;
        }
        return 0;
    }

    /**
     * @Description: 打开跑活力值的更新用户活力值状态
     * @param
     * @return void
     * @author long
     * @date 2019/11/21 19:51
     */
    @Override
    public void openUpdateTask() {
        while (true){
            UvitalityValueList uVitalityValueList = new UvitalityValueList();
            List<UvitalityValueList>  list = uvitalityValueListMapper.selectNeedHandle();
            try{
                if(list.size()>0){
                    uVitalityValueList = list.get(0);
                    UvitalityValueList uvitalityValueList = TaskMethod.updateUvitalityValueList(uVitalityValueList, 2);
                    uvitalityValueListMapper.updateByPrimaryKeySelective(uvitalityValueList);
                    ComponentUtil.taskService.activeValueUpdateUserInfo(uVitalityValueList);
                }else{
                    Thread.sleep(20000);
                }
            }catch (Exception e){
                UvitalityValueList uvitalityValueList = TaskMethod.updateUvitalityValueList(uVitalityValueList,Constant.ACTIVE_VALUE_TYPE3);
                uvitalityValueListMapper.updateByPrimaryKeySelective(uvitalityValueList);
                e.printStackTrace();
            }

//            VcMemberResource   vcMemberResource =TaskMethod.testChangUvitalityValueList(9);
//            vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource) ;

//            List<UvitalityValueList>  list = uvitalityValueListMapper.selectNeedHandle();
//            if(list.size()!=0){
//                UvitalityValueList uvitalityValueList  =TaskMethod.changUvitalityValueList(list);
//                uvitalityValueListMapper.updateByPrimaryKeySelective(uvitalityValueList);
////                ComponentUtil.taskService.updateUserVitalityValue(list);
//            }else{
//                try{
//                    Thread.sleep(600000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Override
    public List<DisWisemanInfo> getNoLoginDisWisemanInfo() {
        List<DisWisemanInfo>  list = disWisemanInfoMapper.selectByPrimaryKey(new DisWisemanInfo());
        return TaskMethod.changNoLoginDisWisemanInfo(list);
    }


    @Override
    public List<DisWisemanInfo> queryUserDisWisemanInfo(Integer memberId)throws Exception {
        VcMemberResource   vcMemberResource =  TaskMethod.changvcMemberResource(memberId);
        VcMember   vcMember = vcMemberMapper.selectByPrimaryKey(TaskMethod.changvcMember(memberId));
        VcMemberResource   vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        VcRewardReceive  rewardReceive  = vcRewardReceiveMapper.selectByPrimaryKey(memberId);

        Map<String,Object> map = TaskMethod.changCurrentAndMaxMap(vcMemberResource1,rewardReceive,vcMember);
        List<DisWisemanInfo>  disWisemanInfoList =disWisemanInfoMapper.selectByPrimaryKey(new DisWisemanInfo());

        disWisemanInfoList = TaskMethod.changDisWisemanInfo(map,disWisemanInfoList);
        return disWisemanInfoList;
    }

    @Override
    public boolean checkExeTaskIdReward(Integer memberId, Integer taskId) {
        VcMemberResource   vcMemberResource =  TaskMethod.changvcMemberResource(memberId);
        VcMemberResource   vcMemberResource1 =vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        VcRewardReceive  rewardReceive  = vcRewardReceiveMapper.selectByPrimaryKey(memberId);
        return  TaskMethod.cheakCondition(vcMemberResource1,rewardReceive,taskId);
    }

    @Override
    public boolean addRewardTaskLog(Integer memberId, Integer taskId) throws Exception {
        DisTaskType   taskType= TaskMethod.changeDisTaskType(taskId);
        if(taskType==null){
            return false;
        }

        UTaskHave        uTaskHave         = TaskMethod.changeAddUTaskHave(memberId,taskId,taskType.getTaskValidityDay(),taskType.getTotalNum(),
                                                                                taskType.getTaskLevel(),taskType.getTaskCircleDay(),taskType.getEveryNum());
        VcRewardReceive  vcRewardReceive   = TaskMethod.changeUpdateRewardReceive(memberId,taskId);
        ComponentUtil.transactionalService.receiveTaskUpdateInfo(uTaskHave,vcRewardReceive);
        return true;
    }

    @Override
    public boolean activeValueUpdateUserInfo(UvitalityValueList uVitalityValueList)throws  Exception {
        //查询用户信息
        VcMember       vcMember   =  TaskMethod.getMember(uVitalityValueList.getMemberId());
        VcMember       vcMember1  =   vcMemberMapper.selectByMemberId(vcMember);
        //查询用户需要修改的
        VcMemberResource  updateMyResource =   TaskMethod.getUqdateMyActiveValue(uVitalityValueList.getMemberId(),uVitalityValueList.getActiveValue(),uVitalityValueList.getSymbolType());

//        List<VcMemberResource>     list    =   vcMemberResourceMapper.selectByTeamActive(updateMyResource);//查询英雄值
//        VcMemberResource   vcMemberResource =  TaskMethod.updateHeroActive(uVitalityValueList.getMemberId(),list);
        int   uqdateCount  =  0;


        if(uVitalityValueList.getRewardType()==1){
            uqdateCount = ComponentUtil.taskService.myActiveValueUpdateResource(updateMyResource,Constant.TASK_TYPE12,uVitalityValueList.getActiveValue());
        }else{
            uqdateCount = ComponentUtil.taskService.myActiveValueUpdateResource(updateMyResource,Constant.TASK_TYPE13,uVitalityValueList.getActiveValue());
        }


//        ComponentUtil.transactionalService.updateMyActiveValue(updateMyResource,vcMember);
        if(uqdateCount==0){
            throw  new ServiceException(ErrorCode.ENUM_ERROR.T000001.geteCode(),ErrorCode.ENUM_ERROR.T000001.geteDesc());
        }

        ComponentUtil.taskService.levelHandle(vcMember1.getMemberId());
        //团队活力值的  需要修改的总体的一个list
        List<Integer>    updateList =   TaskMethod.getSuperiorIdList(vcMember1);

        for(Integer memberId :updateList){  //挨个遍历更新 英雄活力 和联盟活力值
            Integer    memberIdLevel = 0 ;
//            VcMemberResource  updateMyResource1 = TaskMethod.getUqdateTeamActive(memberId,uVitalityValueList.getActiveValue());
            VcMember     updateVcMember   =  TaskMethod.getMember(memberId);
            VcMemberResource   queryVcMemberResource   =   TaskMethod.changvcMemberResource(memberId);
            VcMemberResource   rsVcMemberResource      =   vcMemberResourceMapper.selectByPrimaryKey(queryVcMemberResource); //查询等级信息

            ComponentUtil.transactionalService.updataActiveValue(rsVcMemberResource,updateVcMember);

//            ComponentUtil.taskService.levelHandle(memberId);//更新等级情况
            VcMemberResource      rsVcMemberUpdate     =   vcMemberResourceMapper.selectByPrimaryKey(queryVcMemberResource);
            Integer  level  =  TaskMethod.getLevel(rsVcMemberUpdate); //条件满足等级

//            if(level==rsVcMemberUpdate.getDarenLevel()){
//                continue;
//            }else{
            memberIdLevel = ComponentUtil.taskService.CheckCondition(level,rsVcMemberUpdate.getDarenLevel(),rsVcMemberUpdate.getMemberId());
//            }
            VcMemberResource  vcMemberResource = TaskMethod.updateResourceLevel(rsVcMemberUpdate.getMemberId(),memberIdLevel);
            vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource);
        }




        UvitalityValueList uvitalityValueList = TaskMethod.updateUvitalityValueList(uVitalityValueList,Constant.ACTIVE_VALUE_TYPE4);
        uvitalityValueListMapper.updateByPrimaryKeySelective(uvitalityValueList);


        //筛选用户有哪些需要变更的

        //升星查询（当前等级，上一级条件）



        //等级条件是否满足

        //字段更新

        return false;
    }

    @Override
    public VcMember getTeamExtensionMemberInfo(List<Integer> list, Integer type,Integer activeValue) {
        return null;
    }

    @Override
    public void uqdateLevel(Integer memberId) {
        //当前用户信息
        VcMemberResource   vcMemberResource  = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        VcMemberResource  queryVcMember=vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource); //当前等级情况
        if(queryVcMember==null){//无效的信息
            return;
        }
        //查询实名制的用户信息
        VcMember  vcMember   = TaskMethod.changvcMemberTOsuperiorId(memberId);
        VcMember  rsVcMember = vcMemberMapper.selectByIsCertificationNum(vcMember); //直推人数
        Integer  level  = TaskMethod.getLevel(queryVcMember);
        Integer  darenLevel = ComponentUtil.taskService.CheckCondition(vcMember.getDarenLevel(),level,vcMember.getMemberId());

        VcMemberResource vcMemberResource1 =TaskMethod.updateResourceLevel(memberId,darenLevel);
        if(darenLevel==rsVcMember.getDarenLevel()){
            //等级相等的时候，不需要修改
            return;
        }else if(darenLevel>rsVcMember.getDarenLevel()){
            //等级大于当前等级的时候，需要更新字段，查看奖励是否有送过
            VcRewardReceive  vcRewardReceive = vcRewardReceiveMapper.selectByPrimaryKey(memberId);
            TaskMethod.checkLevelIsReceive(vcRewardReceive,darenLevel);
            vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource1);
        }else if(darenLevel<rsVcMember.getDarenLevel()){
            //等级大于当前等级的时候，需要更新字段
            vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource1);
        }


    }

    /**
     * @Description: 该用户能不能执行更新等级
     * @param level
    * @param currentLevel
     * @return boolean
     * @author long
     * @date 2019/12/6 20:45
     */
    @Override
    public boolean isHaveSeniority(Integer level,Integer currentLevel,Integer memberId) {
        boolean   flag  = true ;
        if(level==currentLevel){
            flag =   false ;
        }else{

        }
        return flag;
    }

    @Override
    public Integer CheckCondition(Integer level, Integer currentLevel, Integer memberId) {
        while(level!=0){
            if(level==1){
                boolean  flag = ComponentUtil.taskService.checkLevel1(memberId,currentLevel);
                if(flag){
                    break;
                }
            }else if(level==2){
                boolean  flag = ComponentUtil.taskService.checkLevel2(memberId,currentLevel);
                if(flag){
                    break;
                }
            }else  if(level==3){
                boolean  flag = ComponentUtil.taskService.checkLevel3(memberId,currentLevel);
                if(flag){
                    break;
                }
            }else if(level==4){
                boolean  flag = ComponentUtil.taskService.checkLevel4(memberId,currentLevel);
                if(flag){
                    break;
                }
            }else if(level==5){
                boolean  flag = ComponentUtil.taskService.checkLevel5(memberId,currentLevel);
                if(flag){
                    break;
                }
            }
            level--;
        }
        return level;
    }

    @Override
    public boolean checkLevel1(Integer memberId,Integer currentLevel) {
//        VcMember    vcMember    =  TaskMethod.changvcMemberTOsuperiorId(memberId);
//        String  level1=(String )ComponentUtil.redisService.get(CacheKey.LEVEL+memberId);
//        if(!StringUtils.isBlank(level1)){
//            ComponentUtil.redisService.set(CacheKey.LEVEL+memberId,"1");
//        }
        return true;
    }

    @Override
    public boolean checkLevel2(Integer memberId,Integer currentLevel) {
        boolean    rsFlag =  false ;
        VcMember    vcMember    =  TaskMethod.getVcMemberLevel2(memberId);
        List<VcMember>    listHero  =  vcMemberMapper.selectIsLevel2Where(vcMember);

        if(listHero.size()<1){
            return false ;
        }
        List<VcMember>    listAll = vcMemberMapper.selectByPid(vcMember);
        List<Integer> idList2 = TaskMethod.removeHeroes(listAll,listHero);
        if(idList2.size()==0){
            return false ;
        }

        VcMemberResource  resource =TaskMethod.quertLevel(idList2,1);

        List<VcMemberResource>   listRs = vcMemberResourceMapper.selectMemberIdLevel(resource);
        if(listRs.size()!=0){
            rsFlag=true;
        }
        return rsFlag;
    }

    @Override
    public boolean checkLevel3(Integer memberId,Integer currentLevel) {
        boolean    rsFlag =  false ;
        VcMember    vcMember    =  TaskMethod.getVcMemberLevel2(memberId);
        List<VcMember>    listHero  =  vcMemberMapper.selectIsLevel2Where(vcMember);
        List<VcMember>    listAll   =  vcMemberMapper.selectByPid(vcMember);

        if(listHero.size()<1){
            return false ;
        }

        List<Integer>     allLiat = null;


        List<Integer>   idList = TaskMethod.removeHeroes(listAll,listHero);
        VcMember    vcMembertwo    =TaskMethod.toIdList(idList);
        List<VcMember>    twoList  =  vcMemberMapper.selectLevel2Above(vcMembertwo);

        List<Integer>  list  =TaskMethod.toQueryMemberList(twoList);
        List<Integer>  memberIdList =ComponentUtil.taskService.getLevelMemberId(list,1);

        allLiat = TaskMethod.addList(idList,list);

        VcMemberResource  resource =TaskMethod.quertLevel(allLiat,2);
        List<VcMemberResource>   listRs = vcMemberResourceMapper.selectMemberIdLevel(resource);
        if(listRs.size()!=0){
            rsFlag=true;
        }
        return rsFlag;
    }

    @Override
    public boolean checkLevel4(Integer memberId,Integer currentLevel) {
        boolean    rsFlag =  false ;
        VcMember    vcMember    =  TaskMethod.getVcMemberLevel2(memberId);
        List<VcMember>    listHero  =  vcMemberMapper.selectIsLevel2Where(vcMember);
        List<VcMember>    listAll   =  vcMemberMapper.selectByPid(vcMember);

        if(listHero.size()<1){
            return false ;
        }
        List<Integer>     allLiat = null;

        List<Integer>   idList = TaskMethod.removeHeroes(listAll,listHero);
        VcMember    vcMembertwo    =TaskMethod.toIdList(idList);
        List<VcMember>    twoList  =  vcMemberMapper.selectLevel2Above(vcMembertwo);

        List<Integer>  list  =TaskMethod.toQueryMemberList(twoList);
        List<Integer>  memberIdList =ComponentUtil.taskService.getLevelMemberId(list,2);

        allLiat = TaskMethod.addList(idList,list);
        allLiat = TaskMethod.addList(allLiat,memberIdList);
        VcMemberResource  resource =TaskMethod.quertLevel(allLiat,3);
        List<VcMemberResource>   listRs = vcMemberResourceMapper.selectMemberIdLevel(resource);
        if(listRs.size()!=0){
            rsFlag=true;
        }

        return rsFlag;
    }


    @Override
    public boolean checkLevel5(Integer memberId,Integer currentLevel) {
        boolean    rsFlag =  false ;
        VcMember    vcMember    =  TaskMethod.getVcMemberLevel2(memberId);
        List<VcMember>    listHero  =  vcMemberMapper.selectIsLevel2Where(vcMember);
        List<VcMember>    listAll   =  vcMemberMapper.selectByPid(vcMember);

        if(listHero.size()<1){
            return false ;
        }
        List<Integer>   idList = TaskMethod.removeHeroes(listAll,listHero);

        List<Integer>     allLiat = null;

        VcMember    vcMembertwo    =TaskMethod.toIdList(idList);
        List<VcMember>    twoList  =  vcMemberMapper.selectLevel2Above(vcMembertwo);

        List<Integer>  list  =TaskMethod.toQueryMemberList(twoList);
        List<Integer>  memberIdList =ComponentUtil.taskService.getLevelMemberId(list,3);
        allLiat = TaskMethod.addList(idList,list);
        allLiat = TaskMethod.addList(allLiat,memberIdList);
        VcMemberResource  resource =TaskMethod.quertLevel(allLiat,4);
        List<VcMemberResource>   listRs = vcMemberResourceMapper.selectMemberIdLevel(resource);
        if(listRs.size()!=0){
            rsFlag=true;
        }

        return rsFlag;
    }

//    @Override
//    public UTaskHave getMemberUDailyTaskStat(Integer memberId) {
//        UTaskHave  uTaskHave = TaskMethod.toUTaskHave(memberId);
//        UTaskHave  uTaskHave1  = uTaskHaveMapper.selectAlreadyNumCount(uTaskHave); //查询用户的奖励金额已经未领取的
//        UTaskHave  uTaskHave2  = uTaskHaveMapper.selectSurplusNumCount(uTaskHave); //查询用户的奖励金额已经未领取的
//        uTaskHave.setAlreadyNumCount(uTaskHave1.getAlreadyNumCount());
//        uTaskHave.setSurplusNumCount(uTaskHave2.getSurplusNumCount());
//        return uTaskHave;
//    }

    @Override
    public UdailyTaskStat getMemberUDailyTaskStat(Integer memberId) {
        UdailyTaskStat udailyTaskStat = TaskMethod.changUdailyTaskStat(memberId);
        UdailyTaskStat udailyTaskStat1 = udailyTaskStatMapper.selectByPrimaryKey(udailyTaskStat);
        if(udailyTaskStat1==null){
            UdailyTaskStat udailyTaskStat2 = SynchroMethod.getUqdateUdailyTaskStat(memberId);
            udailyTaskStatMapper.insertSelective(udailyTaskStat2);
            udailyTaskStat1 = TaskMethod.initUdailyTaskStat(memberId);
        }
        return udailyTaskStat1;
    }

    @Override
    public UTaskHave getMaxUTaskHave(Integer memberId) {
        UTaskHave   uTaskHave  =  TaskMethod.changUTaskHave(memberId);
        List<UTaskHave>  list  =  uTaskHaveMapper.selectByPrimaryKey(uTaskHave);
        uTaskHave  =  TaskMethod.changUTaskHaveMax(list);
        return uTaskHave;
    }

    @Override
    public UMasonryListLog getUMasonryListLog(Integer memberId) {
        UMasonryListLog  toUMasonryListLog = TaskMethod.toUMasonryListLog(memberId);
        return uMasonryListLogMapper.selectByInfoMax(toUMasonryListLog);
    }

    @Override
    public UTaskHave getUTaskHave(Integer memberId) {
        UTaskHave  uTaskHave = TaskMethod.toUTaskHave(memberId);
        UTaskHave  uTaskHave1  = uTaskHaveMapper.selectAlreadyNumCount(uTaskHave); //查询用户的奖励金额已经未领取的
        UTaskHave  uTaskHave2  = uTaskHaveMapper.selectSurplusNumCount(uTaskHave); //查询用户的奖励金额已经未领取的
        if(uTaskHave1==null ||uTaskHave2==null){
            uTaskHave.setAlreadyNumCount(0D);
            uTaskHave.setSurplusNumCount(0D);
        }else{
            uTaskHave.setAlreadyNumCount(uTaskHave1.getAlreadyNumCount());
            uTaskHave.setSurplusNumCount(uTaskHave2.getSurplusNumCount());
        }

        return uTaskHave;
    }

    @Override
    public Double grantReward(VcMemberResource  vcMemberResource,List<UTaskHave>  list) {
        Double  rewardMasonry = 0D;

        Double  activeValue=vcMemberResource.getActiveValue()*Constant.ACTIVE_VALUE_MASONRY;
        Double  taskTask = TaskMethod.statTaskMasonry(list);

        UMasonryListLog  uMasonryListLog= null;
        if(activeValue!=0){
            uMasonryListLog=TaskMethod.changeUMasonryListLog(vcMemberResource.getMemberId(),null, Constant.TASK_TYPE2,Constant.TASK_SYMBOL_TYPE1,activeValue);
        }

        UMasonryListLog  taskTaskLog=TaskMethod.changeUMasonryListLog(vcMemberResource.getMemberId(),null, Constant.TASK_TYPE1,Constant.TASK_SYMBOL_TYPE1,taskTask);
        if(activeValue==0&&taskTask==0){
            return rewardMasonry;
        }

        USubReward   uSubReward =  TaskMethod.toUsubReward(vcMemberResource.getMemberId());
        List<USubReward>   listUSubReward  = uSubRewardMapper.selectByValid(uSubReward);
        Double      uSubReward1  = TaskMethod.countUSubReward(listUSubReward);
        UMasonryListLog  uSubLog  = null;
        if(uSubReward1!=0){
            uSubLog = TaskMethod.changeUMasonryListLog(vcMemberResource.getMemberId(),null, Constant.TASK_TYPE11,Constant.TASK_SYMBOL_TYPE1,uSubReward1);
        }

//        if(taskTask!=0){
//            uMasonryListLogMapper.insertSelective(taskTaskLog);
//        }
        //查看该用户是否在做交易
        String lockKey_send = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, vcMemberResource.getMemberId());
        boolean send = ComponentUtil.redisIdService.lock(lockKey_send);
        rewardMasonry = activeValue + taskTask + uSubReward1;
        VcMemberResource updateResource = TaskMethod.changeUpdateResource(vcMemberResource.getMemberId(),rewardMasonry,0D);

        //生成修改的list 信息
        UTaskHave    uTaskHave =  TaskMethod.updateUTaskHaveEventDay(list);

        if(send){
            ComponentUtil.transactionalService.gratitudeupdateMyActiveValue(uMasonryListLog,taskTaskLog,uSubLog,updateResource,uTaskHave);
        }
        // 解锁
        ComponentUtil.redisIdService.delLock(lockKey_send);

        //修改那些过时任务更新！
        List<UTaskHave>   updateUTaskHave  =   TaskMethod.updateUTaskHaveStat(list);
        ComponentUtil.taskService.exeExpireTask(updateUTaskHave,vcMemberResource.getMemberId());
        return rewardMasonry;
    }


    @Override
    public boolean isReceiveAwards(Integer memberId) {
        UMasonryListLog uMasonryListLog=TaskMethod.queryUMasonryListLog(memberId);
        List<UMasonryListLog>   list = uMasonryListLogMapper.selectByPrimaryKey(uMasonryListLog);
        if(list.size()!=0){
            return false;
        }
        return true;
    }


    @Override
    public List<Integer> getLevelMemberId(List<Integer> memberList, Integer generationNum) {
        List<Integer>  rsList =  new ArrayList<>();
        List<Integer>  list =  memberList;
        for(int  i=1;i<generationNum;i++){
            VcMember    vcMemberThree    =TaskMethod.toIdList(list);
            List<VcMember>    threeList  =  vcMemberMapper.selectIdListAll(vcMemberThree);
            list = TaskMethod.toQueryMemberList(threeList);
            rsList=TaskMethod.addList(rsList,list);
        }
        return rsList;
    }

    /**
     * @Description: 执行修改过期的task 信息
     * @param updateUTaskHave   需要更新task 信息
    * @param memberId          会员id
     * @return void
     * @author long
     * @date 2019/12/22 16:55
     */
    @Override
    public void exeExpireTask(List<UTaskHave> updateUTaskHave, Integer memberId) {
        if(updateUTaskHave.size()==0){
            return;
        }
        if(updateUTaskHave.size()!=0){
            for(UTaskHave uTaskHave1:updateUTaskHave){
                UvitalityValueList uVitalityValueList =TaskMethod.pottingVitalityValue(memberId,Constant.ACTIVE_TYPE8,Constant.TASK_SYMBOL_TYPE2,uTaskHave1.getTotalNum());
                ComponentUtil.transactionalService.taskExpireUpdateInfo(uTaskHave1,uVitalityValueList);
            }
        }

    }



    /**
     * @Description: 活力值变更需要更新 资源表的砖石，
     *                  同时也需要添加一条砖石变动的记录
     * @param updateMyResource
     * @return void
     * @author long
     * @date 2019/12/22 17:45
     */
    @Override
    public int myActiveValueUpdateResource(VcMemberResource updateMyResource,Integer type,Double masonry) {
        //砖石明细表添加记录
        UMasonryListLog   uMasonryListLog = null;

        //汇总表修改记录
        UMasonrySummary   uMasonrySummary = null;
        if (type==Constant.TASK_TYPE12){
            uMasonryListLog = TaskMethod.changeUMasonryListLog(updateMyResource.getMemberId(),null, type,Constant.TASK_SYMBOL_TYPE1,masonry);
            uMasonrySummary = TaskMethod.updateUMasonrySummary(updateMyResource.getMemberId(),Constant.TASK_SYMBOL_TYPE1,masonry);
        }else{
            uMasonryListLog =  TaskMethod.changeUMasonryListLog(updateMyResource.getMemberId(),null, type,Constant.TASK_SYMBOL_TYPE2,masonry);
            uMasonrySummary = TaskMethod.updateUMasonrySummary(updateMyResource.getMemberId(),Constant.TASK_SYMBOL_TYPE2,masonry);
        }

        String lockKey_send = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, updateMyResource.getMemberId());
        boolean send = ComponentUtil.redisIdService.lock(lockKey_send);
        if(send){
            ComponentUtil.transactionalService.myActiveValueUpdate(uMasonryListLog,uMasonrySummary,updateMyResource);
        }else{
            return 0;
        }

        // 解锁
        ComponentUtil.redisIdService.delLock(lockKey_send);
        return  1;
    }

    @Override
    public int insertUMasonryListLog(UMasonryListLog listLog) {
        return uMasonryListLogMapper.insertSelective(listLog);
    }

    @Override
    public void updateHaveState(){
        while (true){
            try{
                UTaskHave  uTaskHave =TaskMethod.updateUTaskHave();
                List<UTaskHave> list1 =uTaskHaveMapper.selectInvalid(uTaskHave);
                if(list1.size()==0){
                    Thread.sleep(Constant.UPDATE_TASK_TIME);
                    continue;
                }
                List<Long>  list = new ArrayList<>();
                for(UTaskHave uTaskHave1:list1){
                    list.add(uTaskHave1.getId());
                }

                UTaskHave  d = TaskMethod.updateUTaskHave(list,Constant.CURRENT_STATE_TYPE3);
                uTaskHaveMapper.updateCurrentStateInvalid(d);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void levelHandle(Integer memberId) {
        VcMemberResource   queryVcMemberResource   =   TaskMethod.changvcMemberResource(memberId);
        VcMemberResource      rsVcMemberUpdate     =   vcMemberResourceMapper.selectByPrimaryKey(queryVcMemberResource);

        Integer         level      =   TaskMethod.getLevel(rsVcMemberUpdate); //条件满足等级
        Integer    memberIdLevel   =   ComponentUtil.taskService.CheckCondition(level,rsVcMemberUpdate.getDarenLevel(),rsVcMemberUpdate.getMemberId());
//        if(level!=memberIdLevel){
        VcMemberResource  vcMemberResource = TaskMethod.updateResourceLevel(rsVcMemberUpdate.getMemberId(),memberIdLevel);
        vcMemberResourceMapper.updateByPrimaryKeySelective(vcMemberResource);
//        }
    }

    @Override
    public void updateMyLevel(Integer memberId) {

    }
}
