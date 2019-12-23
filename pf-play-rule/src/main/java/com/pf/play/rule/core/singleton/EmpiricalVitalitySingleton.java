package com.pf.play.rule.core.singleton;

import com.pf.play.rule.core.model.DisEmpiricalValueLevel;
import com.pf.play.rule.core.model.DisVitalityValue;
import com.pf.play.rule.core.model.SysTypeDictionary;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/6 17:53
 * @Version 1.0
 */
public class EmpiricalVitalitySingleton {

    private static EmpiricalVitalitySingleton empiricalVitalitySingleton;
    private static ReentrantLock lock = new ReentrantLock();

    public List<DisVitalityValue> disVitalityValue ;


    public List<DisEmpiricalValueLevel> disEmpiricalValueLevel ;

//

    private EmpiricalVitalitySingleton() {

    }

    public static EmpiricalVitalitySingleton getInstance() {
        if (empiricalVitalitySingleton == null) {
            lock.lock();
            if (empiricalVitalitySingleton == null) {
                empiricalVitalitySingleton = new EmpiricalVitalitySingleton();
            }
            lock.unlock();
        }
        return empiricalVitalitySingleton;
    }

    public List<DisVitalityValue> getDisVitalityValue() {
        return disVitalityValue;
    }

    public void setDisVitalityValue(List<DisVitalityValue> disVitalityValue) {
        this.disVitalityValue = disVitalityValue;
    }

    public List<DisEmpiricalValueLevel> getDisEmpiricalValueLevel() {
        return disEmpiricalValueLevel;
    }

    public void setDisEmpiricalValueLevel(List<DisEmpiricalValueLevel> disEmpiricalValueLevel) {
        this.disEmpiricalValueLevel = disEmpiricalValueLevel;
    }
}
