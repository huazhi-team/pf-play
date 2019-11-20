package com.pf.play.rule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.pf.play.rule.core.mapper")
@EnableScheduling//开启定时器任务
public class PfPlayRuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfPlayRuleApplication.class, args);
    }

}
