package com.pf.play.rule.core.runner;

import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author df
 * @Description:定时任务：文件上传
 * @create 2019-08-15 9:48
 **/
@Component
@EnableScheduling
public class TaskFileUpload {
    private final static Logger log = LoggerFactory.getLogger(TaskFileUpload.class);

//    /**
//     * 早上10点启动
//     */
//    @Scheduled(cron = "0 0 10 * * ?")
    //凌晨1点启动
    @Scheduled(cron = "0 0 1 * * ?")
    public void job1(){
        System.out.println("定时任务：" + new Date());
    }

    @Scheduled(cron = "1 * * * * ?")
    public void job2(){
//        System.out.println("定时任务job2：" + new Date());
//
//        List<AppModel> dataList = ComponentUtil.appService.getListApp(new AppModel());
//        for (AppModel dataModel : dataList){
//            log.info("id:" + dataModel.getId());
//            log.info("appName:" + dataModel.getAppName());
//            log.info("payCodeName:" + dataModel.getPayCodeName());
//        }
    }
}
