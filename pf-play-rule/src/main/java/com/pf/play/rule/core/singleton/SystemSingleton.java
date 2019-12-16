package com.pf.play.rule.core.singleton;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 字典表的属性初始化
 * @Author long
 * @Date 2019/12/16 20:56
 * @Version 1.0
 */
public class SystemSingleton {

    private static SystemSingleton systemSingleton;
    private static ReentrantLock lock = new ReentrantLock();

    private SystemSingleton() {

    }
    public static SystemSingleton getInstance() {
        if (systemSingleton == null) {
            lock.lock();
            if (systemSingleton == null) {
                systemSingleton = new SystemSingleton();
            }
            lock.unlock();
        }
        return systemSingleton;
    }


}
