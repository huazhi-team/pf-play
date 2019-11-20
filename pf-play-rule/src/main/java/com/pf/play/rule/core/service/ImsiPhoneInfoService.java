package com.pf.play.rule.core.service;



import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.ImsiPhoneInfoModel;

import java.util.List;

/**
 * @author df
 * @Description:设备imsi对应手机号信息的Service层
 * @create 2019-05-23 11:32
 **/
public interface ImsiPhoneInfoService<T> extends BaseService<T> {

    /**
     * 根具省份、运营商随机获取一条数据
     * @param model
     * @return
     */
    public ImsiPhoneInfoModel randomImsiPhoneInfoModel(ImsiPhoneInfoModel model);

    /**
     * @Description: TODO(根据省份、运营商获取10000条数据)
     * @author df
     * @param model
     * @create 17:33 2019/6/14
     **/
    public List<ImsiPhoneInfoModel> getImsiPhoneInfoList(ImsiPhoneInfoModel model);


    /**
     * @Description: TODO(查询各个省份以及对应的运营商)
     * @author df
     * @create 13:34 2019/6/22
     **/
    public List<ImsiPhoneInfoModel> getProvinceAndProvider();


    /**
     * @Description: TODO(根据省份、运营商查询数据)
     * @author df
     * @param model
     * @create 13:38 2019/6/22
     **/
    public List<ImsiPhoneInfoModel> getImsiPhoneInfoByProvinceAndProvider(ImsiPhoneInfoModel model);
}
