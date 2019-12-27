package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.TaskHodgepodgeMapper;
import com.pf.play.rule.core.model.task.TaskAlipayNotifyModel;
import com.pf.play.rule.core.service.TaskHodgepodgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 定时任务：大杂烩的Service层的实现层
 * @Author yoko
 * @Date 2019/12/27 22:09
 * @Version 1.0
 */
@Service
public class TaskHodgepodgeServiceImpl <T> extends BaseServiceImpl<T> implements TaskHodgepodgeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private TaskHodgepodgeMapper taskHodgepodgeMapper;


    public BaseDao<T> getDao() {
        return taskHodgepodgeMapper;
    }

    @Override
    public List<TaskAlipayNotifyModel> getTaskAlipayNotify(Object obj) {
        return taskHodgepodgeMapper.getTaskAlipayNotify(obj);
    }

    @Override
    public int updateTaskAlipayNotifyStatus(Object obj) {
        return taskHodgepodgeMapper.updateTaskAlipayNotifyStatus(obj);
    }

    @Override
    public int updateConsumerIsPay(Object obj) {
        return taskHodgepodgeMapper.updateConsumerIsPay(obj);
    }
}
