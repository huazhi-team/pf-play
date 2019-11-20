package com.pf.play.rule.core.common.redis;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author df
 * @Description:根据redis获取唯一id值
 * @create 2018-12-13 15:01
 **/
@Service
public class RedisIdService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getId() throws Exception {
        String formatDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String key = "RedisIdService_key" + formatDate;
        Long incr = getIncr(key);
        //七位序列号
        DecimalFormat df = new DecimalFormat("0000000");
        return formatDate + df.format(incr);
    }

    public Long getIncr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory(), 1L);
        long increment = entityIdCounter.getAndIncrement();
        if (increment < 2) {
            entityIdCounter.expire(1, TimeUnit.DAYS);
        }
        return increment;
    }

    /**
     * @Description: 保证原子性得到redis中的数值：默认1天的存储
     * @param key---redis中的key
     * @param expire---设置过期的时间（day）
     * @return 返回的Long为原子性加一以后的
     * @author yoko
     * @date 2019/11/12 22:37
     */
    public Long getIncr(String key, int expire) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        /*
        incrementAndGet返回的是新值（即加1后的值）
        getAndIncrement法是返回旧值（即加1前的原始值）
         */
        long increment = entityIdCounter.incrementAndGet();
        if (increment < 2) {
            entityIdCounter.expire(expire, TimeUnit.DAYS);
        }
        return increment;
    }


    private final String LOCKVALUE = "lockvalue";
    private boolean locked = false;

    /**
     * @Description: TODO(redis锁)
     * @author df
     * @param lockKey-要锁的key
     * @create 16:37 2019/1/31
     **/
    public synchronized boolean lock(String lockKey){
        /*该方法会在没有key时，设置key;存在key时返回false；因此可以通过该方法及设置key的有效期，判断是否有其它线程持有锁*/
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,LOCKVALUE);
        if(success != null && success){
            redisTemplate.expire(lockKey,5,TimeUnit.SECONDS);
            locked = true;
        }else{
            locked = false;
        }
        return locked;
    }

    /**
     * @Description: TODO(redis解锁)
     * @author df
     * @param lockKey-要删除的锁的key
     * @create 16:37 2019/1/31
     **/
    public synchronized void delLock(String lockKey){
        redisTemplate.delete(lockKey);
    }
}
