package com.pf.play.rule.core.service.impl;

import com.pf.play.common.utils.DateUtil;
import com.pf.play.common.utils.UUIDUtils;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.mapper.VcMemberGenerateMapper;
import com.pf.play.rule.core.model.VcMemberGenerateModel;
import com.pf.play.rule.core.service.GenerateService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 15:01
 * @Version 1.0
 */
@Service
public class GenerateServiceImpl<T> extends BaseServiceImpl<T> implements GenerateService<T> {

    private int  ONE_YEAR =366;

    @Autowired
    private VcMemberGenerateMapper vcThirdPartyMapper;


    @Override
    public BaseDao<T> getDao() {
        return vcThirdPartyMapper;
    }

    /**
     * @Description: 根据类型获取
     * @param type 1、TOKEN  2、INVITE_CODE 3、TRADING_ADDRESS
     * @return 生成的缓存中没有的
     * @author long
     * @date 2019/11/13 15:39
     */
    @Override
    public String getNonexistentInformation(int type)throws  Exception {
        String    rs ="";
        Object    reStr ="";
        do{
            if (type== Constant.TOKEN){
                rs = UUIDUtils.createUUID();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, rs);
                reStr = ComponentUtil.redisService.get(token1);
            }else if(type==Constant.INVITE_CODE){
                rs  =   UUIDUtils.createInviteCode();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, rs);
                reStr= ComponentUtil.redisService.get(token1);
            }else if(type == Constant.TRADING_ADDRESS){
                rs = UUIDUtils.createUUID();
                String token1 = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, rs);
                reStr= ComponentUtil.redisService.get(token1);
            }else if(type == Constant.MEMBERID){
                AtomicLong atomic_did = new AtomicLong(ComponentUtil.redisIdService.getIncr(CacheKey.MEMBER_ID_INFO, ONE_YEAR));
                long did = atomic_did.get();
                reStr=did+"";
            }
        }while(reStr!=null);

        return rs;
    }

    @Override
    public Integer getMemberId()throws  Exception   {
        Integer             memberid =0;
        try{
            VcMemberGenerateModel  vcMemberGenerateModel = new VcMemberGenerateModel();
            Integer  timestamp =Integer.parseInt(DateUtil.timeStamp());
            vcMemberGenerateModel.setCreateTime(timestamp);
            vcMemberGenerateModel.setUpdateTime(timestamp);
             vcThirdPartyMapper.insertSelective(vcMemberGenerateModel);
            memberid= vcMemberGenerateModel.getId() ;
        }catch (Exception e){
            e.printStackTrace();
        }

        return memberid;
    }
}
