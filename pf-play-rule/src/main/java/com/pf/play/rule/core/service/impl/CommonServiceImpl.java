package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.SysTypeDictionaryMapper;
import com.pf.play.rule.core.model.SysTypeDictionary;
import com.pf.play.rule.core.service.CommonService;
import com.pf.play.rule.core.singleton.RegisterSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/16 15:52
 * @Version 1.0
 */
@Service
public class CommonServiceImpl<T> extends BaseServiceImpl<T> implements CommonService<T> {

    @Autowired
    private SysTypeDictionaryMapper sysTypeDictionaryMapper;

    @Override
    public BaseDao<T> getDao() {
        return sysTypeDictionaryMapper;
    }

    /**
     * @Description: 类型初始化信息
     * @param
     * @return void
     * @author long
     * @date 2019/11/17 12:25
     */
    @Override
    public void quertCommenType() {
        List<SysTypeDictionary>list = sysTypeDictionaryMapper.selectByPrimaryKey();
        List<SysTypeDictionary>  masonryType = new ArrayList<>();
        List<SysTypeDictionary>  empiricalType = new ArrayList<>();
        List<SysTypeDictionary>  vitalityType = new ArrayList<>();
        List<SysTypeDictionary>  taskType = new ArrayList<>();
        List<SysTypeDictionary>  procedType = new ArrayList<>();
        for(SysTypeDictionary sysTypeDictionary:list){
            if(sysTypeDictionary.getTypeValue().equals("1")){
                masonryType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("2")){
                empiricalType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("3")){
                vitalityType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("4")){
                taskType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("6")){
                procedType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("7")){
                RegisterSingleton.getInstance().setRealNameReward(Double.valueOf(sysTypeDictionary.getValue()));
            }else if(sysTypeDictionary.getTypeValue().equals("8")){
                RegisterSingleton.getInstance().setRealNameCycle(Integer.parseInt(sysTypeDictionary.getValue()));
            }else if(sysTypeDictionary.getTypeValue().equals("9")){
                RegisterSingleton.getInstance().setInitEmpiricalValue(Double.valueOf(sysTypeDictionary.getValue()));
            }
        }
        RegisterSingleton.getInstance().setMasonryType(masonryType);
        RegisterSingleton.getInstance().setEmpiricalType(empiricalType);
        RegisterSingleton.getInstance().setVitalityType(vitalityType);
        RegisterSingleton.getInstance().setTaskType(taskType);
        RegisterSingleton.getInstance().setProcedType(procedType);
    }
}
