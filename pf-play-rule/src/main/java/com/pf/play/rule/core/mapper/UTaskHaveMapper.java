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
}