package com.pf.play.rule.core.runner;

import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
@EnableScheduling
public class SynchroRunner {
    private final static Logger log = LoggerFactory.getLogger(SynchroRunner.class);


    @Value("${task.limit.num}")
    private int limitNum;

//    @Scheduled(cron = "1 * * * * ?")
//    public void synchroPostMemberIdOrSuperiorId() throws Exception{
//        log.info("==============orderTradeCheckTimeOver=====================start");
//        ComponentUtil.userInfoSevrice.superiorSynchronousQhr();
//    }
}
