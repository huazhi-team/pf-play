package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.*;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 15:15
 * @Version 1.0
 */

public interface TaskService  <T> extends BaseService<T> {
    public List<UTaskHave> getMyTask(Integer member_id);

    public int   typeToReceiveTask(String  token,Integer member_id,Integer type);


    /*************根据用户信息查询任务信息****************/
    List<UTaskHave>  queryInvalidHaveTask(Integer member_id)throws  Exception;

    /***************满足最大 Task 信息**********************/
    boolean    checkSatisfyMaxTask(List<UTaskHave> list);

    /******************任务奖励*************/
    void    taskReward(List<UTaskHave> list,Integer memberId) throws Exception;

    /**********   用户初始化奖励   ***************/
    void    userRegisterToTask(Integer  memberid) throws Exception ;

    List<DisTaskType>   queryReceiveTask(Integer  memberid);


    boolean   checkUserCondition(Integer memberId,Integer taskId)throws Exception; //条件是否满足
    //添加记录
    boolean   addUserTask(Integer memberId,Integer taskId)throws Exception;
    //奖励发放
    boolean   addUserGrantReward(Integer memberId,Integer taskId)throws Exception;

    void  updateUserVitalityValue(UvitalityValueList uvitalityValueList);

    void  updateUserVitalityValueType(UvitalityValueList uvitalityValueList, Integer type);

    int  updateUserInfoToVitalityValue(VcMemberResource vcMemberResource,UvitalityValueList uvitalityValueList);

    void  openUpdateTask();

    List<DisWisemanInfo>  getNoLoginDisWisemanInfo() throws Exception;

    List<DisWisemanInfo>  queryUserDisWisemanInfo(Integer member) throws Exception ;

    boolean  checkExeTaskIdReward(Integer memberId,Integer taskId);

    boolean  addRewardTaskLog(Integer memberId, Integer taskId)throws Exception;

    boolean  activeValueUpdateUserInfo(UvitalityValueList  uVitalityValueList) throws  Exception;

    VcMember getTeamExtensionMemberInfo(List<Integer> list,Integer type,Integer activeValue);

    void     uqdateLevel(Integer memberId);

    boolean  isHaveSeniority(Integer level,Integer currentLevel,Integer memberId);

    Integer  CheckCondition(Integer level,Integer currentLevel,Integer memberId);

    boolean  checkLevel1(Integer memberId,Integer currentLevel);

    boolean  checkLevel2(Integer memberId,Integer currentLevel);

    boolean  checkLevel3(Integer memberId,Integer currentLevel);

    boolean  checkLevel4(Integer memberId,Integer currentLevel);

    boolean  checkLevel5(Integer memberId,Integer currentLevel);

    UdailyTaskStat  getMemberUDailyTaskStat(Integer memberId);

    UTaskHave       getMaxUTaskHave(Integer memberId);

    UMasonryListLog  getUMasonryListLog(Integer memberId);

    UTaskHave   getUTaskHave(Integer memberId);


    Double   grantReward(VcMemberResource  vcMemberResource,List<UTaskHave>  list);

//    void   vitalitySettlement();



}
