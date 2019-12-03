package com.pf.play.rule.core.runner;

import com.pf.play.common.utils.CRC32Util;
import com.pf.play.rule.core.common.redis.RedisIdService;
import com.pf.play.rule.core.common.redis.RedisService;
import com.pf.play.rule.core.service.*;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(0)
public class AutowireRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(AutowireRunner.class);

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    @Value("${sp.send.url}")
    private String spSendUrl;

    @Autowired
    private RedisIdService redisIdService;
    @Autowired
    private RedisService redisService;


    @Autowired
    private TaskService taskService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private GenerateService generateService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoSevrice userInfoSevrice;

    @Autowired
    private UserMasonryService userMasonryService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TransactionalService transactionalService;

    @Autowired
    private InitService initService;

    @Autowired
    private ConsumerFixedService consumerFixedService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private VirtualCoinPriceService virtualCoinPriceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;



    Thread runThread = null;









    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AutowireRunner ...");
        ComponentUtil.redisIdService = redisIdService;
        ComponentUtil.redisService = redisService;
//        ComponentUtil.appService = appService;
        ComponentUtil.taskService = taskService;
        ComponentUtil.registerService = registerService;
        ComponentUtil.generateService = generateService;
        ComponentUtil.loginService = loginService;
        ComponentUtil.userInfoSevrice = userInfoSevrice;
        ComponentUtil.userMasonryService = userMasonryService;
        ComponentUtil.rewardService = rewardService;
        ComponentUtil.commonService = commonService;
        ComponentUtil.transactionalService = transactionalService;
        ComponentUtil.initService = initService;
        ComponentUtil.consumerFixedService = consumerFixedService;
        ComponentUtil.strategyService = strategyService;
        ComponentUtil.virtualCoinPriceService = virtualCoinPriceService;
        ComponentUtil.orderService = orderService;
        ComponentUtil.tradeService = tradeService;


        runThread = new RunThread();
        runThread.start();
        CRC32Util.getCRC32("sssss");
//        List<AppModel> dataList = ComponentUtil.appService.getListApp(new AppModel());
//        for (AppModel dataModel : dataList){
//            log.info("id:" + dataModel.getId());
//            log.info("appName:" + dataModel.getAppName());
//            log.info("payCodeName:" + dataModel.getPayCodeName());
//        }






    }

    /**
     * @author df
     * @Description: TODO(模拟请求)
     * <p>1.随机获取当日金额的任务</p>
     * <p>2.获取代码信息</p>
     * @create 20:21 2019/1/29
     **/
    class RunThread extends Thread{
        @Override
        public void run() {
            log.info("启动啦............");
        }


    }






}
