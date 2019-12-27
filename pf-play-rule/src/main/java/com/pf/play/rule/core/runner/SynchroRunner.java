package com.pf.play.rule.core.runner;

import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/10 10:27
 * @Version 1.0
 */

@Component
@Order(900)
public class SynchroRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(SynchroRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /****************  初始化信息 ************/

        log.debug("更新task状态");

        try{
            new Thread() {
                public void run() {
                    log.debug("更新经验值信息！");
                    ComponentUtil.taskService.updateHaveState();
                }
            }.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
