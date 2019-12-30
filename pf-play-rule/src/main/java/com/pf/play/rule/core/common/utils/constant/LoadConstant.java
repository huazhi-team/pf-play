package com.pf.play.rule.core.common.utils.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 加载的配置文件
 * @Author yoko
 * @Date 2019/12/30 12:37
 * @Version 1.0
 */
@Component
public class LoadConstant {

    /**
     * 登录用户token同步
     */
    @Value("${data.synchro.url.token}")
    public  String synchroToken;
    /**
     * 上下级关系同步
     */
    @Value("${data.synchro.url.relation}")
    public String synchroRelation;
}
