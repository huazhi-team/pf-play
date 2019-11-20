package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UserInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/11 14:29
 * @Version 1.0
 */
@Mapper
public interface UserInfoMapper<T> extends BaseDao<T> {
    UserInfoModel selectByUserInfo (UserInfoModel userInfoModel);
}
