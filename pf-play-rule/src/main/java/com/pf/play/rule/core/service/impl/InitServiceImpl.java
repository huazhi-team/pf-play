package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.service.InitService;
import com.pf.play.rule.core.service.UserInfoSevrice;
import com.pf.play.rule.core.singleton.EmpiricalVitalitySingleton;
import com.pf.play.rule.core.singleton.TaskSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/20 10:33
 * @Version 1.0
 */
@Service
public class InitServiceImpl<T> extends BaseServiceImpl<T> implements InitService<T> {

    @Autowired
    private DisTaskTypeMapper disTaskTypeMapper;

    @Autowired
    private DisVitalityValueMapper disVitalityValueMapper;

    @Autowired
    private DisTaskAttributeMapper disTaskAttributeMapper;

    @Autowired
    private DisEmpiricalValueLevelMapper disEmpiricalValueLevelMapper;

    @Autowired
    private DisEmpiricalVitalityAttributeMapper disEmpiricalVitalityAttributeMapper;

    @Override
    public BaseDao<T> getDao() {
        return disTaskTypeMapper;
    }

    /**
     * @Description: 任务初始化加载，不用频繁访问数据库获取
     * @param
     * @return void
     * @author long
     * @date 2019/11/20 11:02
     */
    @Override
    public void initTask() {
        DisTaskType  disTaskType = new DisTaskType();
        List<DisTaskType>  list = disTaskTypeMapper.selectByPrimaryKey(disTaskType);
        TaskSingleton.getInstance().setDisTaskTypeList(list);//任务类型明细表

        DisTaskAttribute    disTaskAttribute = new DisTaskAttribute();
        List<DisTaskAttribute>  attributeList  = disTaskAttributeMapper.selectByPrimaryKey(disTaskAttribute);

        List<DisTaskAttribute>  attributeList1 =new ArrayList<>();
        List<DisTaskAttribute>  attributeList2 =new ArrayList<>();
        List<DisTaskAttribute>  attributeList3 =new ArrayList<>();
        for(DisTaskAttribute disTaskAttribute1:attributeList){
            if(disTaskAttribute1.getAttributeType()==1){
                attributeList1.add(disTaskAttribute1);
            }else if(disTaskAttribute1.getAttributeType()==2){
                attributeList2.add(disTaskAttribute1);
            }else if(disTaskAttribute1.getAttributeType()==3){
                attributeList3.add(disTaskAttribute1);
            }
        }
        TaskSingleton.getInstance().setDisTaskAttributeList(attributeList);//所有的任务属性表

        TaskSingleton.getInstance().setAttributeTypeList1(attributeList1);//条件任务属性
        TaskSingleton.getInstance().setAttributeTypeList2(attributeList2);//奖励任务属性
        TaskSingleton.getInstance().setAttributeTypeList3(attributeList3);//消耗任务属性
    }

    @Override
    public void initVitalityInfo() {
        List<DisVitalityValue> list =  disVitalityValueMapper.selectByPrimaryKey();
        EmpiricalVitalitySingleton.getInstance().setDisVitalityValue(list);

        List<DisEmpiricalValueLevel> listEmpirical =disEmpiricalValueLevelMapper.selectByPrimaryKey();
        EmpiricalVitalitySingleton.getInstance().setDisEmpiricalValueLevel(listEmpirical);
    }

    @Override
    public void initEmpiricalInfo() {

    }

    @Override
    public void initEmpiricalVitalityAttribute() {
        List<DisEmpiricalVitalityAttribute> list = disEmpiricalVitalityAttributeMapper.selectByAll();
        List<DisEmpiricalVitalityAttribute>  attributeList1 =new ArrayList<>();
        List<DisEmpiricalVitalityAttribute>  attributeList2 =new ArrayList<>();

        for(DisEmpiricalVitalityAttribute attribute:list){
            DisEmpiricalVitalityAttribute  disEmpiricalVitalityAttribute = new DisEmpiricalVitalityAttribute();
            BeanUtils.copy(attribute,disEmpiricalVitalityAttribute);
            if(attribute.gettId()==2&&attribute.getAttributeType()==1){
                attributeList1.add(disEmpiricalVitalityAttribute);
            }else if(attribute.gettId()==2&&attribute.getAttributeType()==2){
                attributeList2.add(disEmpiricalVitalityAttribute);
            }
        }
        TaskSingleton.getInstance().setDisVitalityAttribute1(attributeList1);
        TaskSingleton.getInstance().setDisVitalityAttribute2(attributeList2);
    }

    @Override
    public void initSystemDictionary() {

    }
}
