package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UTaskHave;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UTaskHaveMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UTaskHave record);

    int insertSelective(UTaskHave record);

    List<UTaskHave> selectByPrimaryKey(UTaskHave record);

    List<UTaskHave>  selectValidTask(Integer  memberId);

    List<UTaskHave> selectNoValidTask(UTaskHave record);

    int updateByPrimaryKeySelective(UTaskHave record);

    int updateByPrimaryKey(UTaskHave record);

    UTaskHave selectAlreadyNumCount(UTaskHave record);

    UTaskHave selectSurplusNumCount(UTaskHave record);

    //每个任务领取的时候，修改该用户详细信息
    int updateTaskCount(UTaskHave record);

    //每次用户修改完，是否需要到期或者完成
    int updateCurrentState(UTaskHave record);

    //查询是否有过期的任务列表
    List<UTaskHave> selectInvalid(UTaskHave record);

    //修改过期的列表状态
    int updateCurrentStateInvalid(UTaskHave record);


}