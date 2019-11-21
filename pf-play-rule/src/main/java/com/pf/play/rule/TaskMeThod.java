package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.core.model.DateModel;
import com.pf.play.rule.core.model.UvitalityValueList;
import com.pf.play.rule.core.model.VcMemberResource;
import com.pf.play.rule.core.model.VitalityValueModel;
import com.pf.play.rule.util.ComponentUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/20 20:41
 * @Version 1.0
 */
public class TaskMeThod {
    /**
     * @Description: TODO
     * @param memberId
    * @param type
    * @param symbo
    * @param activeValue
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/20 20:33
     */
    public static UvitalityValueList pottingVitalityValue(Integer memberId, Integer type,
                                                          Integer symbo, Float activeValue){
        UvitalityValueList  uvitalityValueList = new  UvitalityValueList();
        DateModel  dateModel  = TaskMeThod.getDate();
        BeanUtils.copy(dateModel,uvitalityValueList);
        uvitalityValueList.setMemberId(memberId);
        uvitalityValueList.setRewardType(type);
        uvitalityValueList.setSymbolType(symbo);
        uvitalityValueList.setActiveValue(activeValue);
        uvitalityValueList.setIsValid(1);
        return   uvitalityValueList;
    }

    /**
     * @Description: 获取当前系统时间
     * @param
     * @return com.pf.play.rule.core.model.DateModel
     * @author long
     * @date 2019/11/20 22:15
     */
    public  static DateModel  getDate(){
        Date   date  = new Date();
        DateModel dateModel = new DateModel();
        dateModel.setCurday(DateUtil.getDayNumber(date));
        dateModel.setCurhour(DateUtil.getHour(date));
        dateModel.setCurminute(DateUtil.getCurminute(date));
        dateModel.setCreateTime(date);
        dateModel.setUpdateTime(date);
        return dateModel;
    }

    /**
     * @Description: 活力值修改状态转换
     * @param uvitalityValue
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/21 11:31
     */
    public  static  UvitalityValueList toUqdateInfo(UvitalityValueList uvitalityValue){
        UvitalityValueList uvitalityValueList = new UvitalityValueList();
        uvitalityValueList.setId(uvitalityValue.getId());
        uvitalityValueList.setIsCount(uvitalityValue.getIsCount());
        return  uvitalityValueList;
    }


    public static VitalityValueModel   vitalityValueToVitalityValueModel(List<UvitalityValueList> uvitalityValueList ){
        VitalityValueModel valueModel = new  VitalityValueModel();

        return valueModel;
    }

}
