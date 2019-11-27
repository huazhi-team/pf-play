package com.pf.play.rule.core.runner;

import com.pf.play.rule.util.ComponentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/12 11:46
 * @Version 1.0
 */
@Component
@Order(100)
public class RegisTerRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(RegisTerRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /****************  初始化信息 ************/
        log.debug("初始化信息！");
        ComponentUtil.registerService.initDate();

        log.debug("类型初始化行为！");
        ComponentUtil.commonService.quertCommenType();

        log.debug("任务类型初始化！");
        ComponentUtil.initService.initTask();
//
//        log.debug("更新用户活力值明细表！");
//        ComponentUtil.taskService.openUpdateTask();

    }

}
