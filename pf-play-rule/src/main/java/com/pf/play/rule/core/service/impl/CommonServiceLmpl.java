package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.SysTypeDictionaryMapper;
import com.pf.play.rule.core.model.SysTypeDictionary;
import com.pf.play.rule.core.service.AipResultInfoService;
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
public class CommonServiceLmpl<T> extends BaseServiceImpl<T> implements CommonService<T> {

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
        for(SysTypeDictionary sysTypeDictionary:list){
            if(sysTypeDictionary.getTypeValue().equals("1")){
                masonryType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("2")){
                empiricalType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("3")){
                vitalityType.add(sysTypeDictionary);
            }else if(sysTypeDictionary.getTypeValue().equals("4")){
                taskType.add(sysTypeDictionary);
            }
        }
        RegisterSingleton.getInstance().setMasonryType(masonryType);
        RegisterSingleton.getInstance().setEmpiricalType(empiricalType);
        RegisterSingleton.getInstance().setVitalityType(vitalityType);
        RegisterSingleton.getInstance().setTaskType(taskType);
    }
}
