package com.pf.play.rule.core.singleton;

import com.pf.play.rule.core.model.DisTaskAttribute;
import com.pf.play.rule.core.model.DisTaskType;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/20 10:22
 * @Version 1.0
 */
public class TaskSingleton {
    private static TaskSingleton taskSingleton;
    private static ReentrantLock lock = new ReentrantLock();

    private TaskSingleton() {

    }

    public static TaskSingleton getInstance() {
        if (taskSingleton == null) {
            lock.lock();
            if (taskSingleton == null) {
                taskSingleton = new TaskSingleton();
            }
            lock.unlock();
        }
        return taskSingleton;
    }

    public List<DisTaskType> disTaskTypeList ;
    public List<DisTaskAttribute> disTaskAttributeList ;

    public List<DisTaskAttribute> attributeTypeList1 ;
    public List<DisTaskAttribute> attributeTypeList2 ;
    public List<DisTaskAttribute> attributeTypeList3 ;



    public List<DisTaskType> getDisTaskTypeList() {
        return disTaskTypeList;
    }

    public void setDisTaskTypeList(List<DisTaskType> disTaskTypeList) {
        this.disTaskTypeList = disTaskTypeList;
    }

    public List<DisTaskAttribute> getDisTaskAttributeList() {
        return disTaskAttributeList;
    }

    public void setDisTaskAttributeList(List<DisTaskAttribute> disTaskAttributeList) {
        this.disTaskAttributeList = disTaskAttributeList;
    }

    public List<DisTaskAttribute> getAttributeTypeList1() {
        return attributeTypeList1;
    }

    public void setAttributeTypeList1(List<DisTaskAttribute> attributeTypeList1) {
        this.attributeTypeList1 = attributeTypeList1;
    }

    public List<DisTaskAttribute> getAttributeTypeList2() {
        return attributeTypeList2;
    }

    public void setAttributeTypeList2(List<DisTaskAttribute> attributeTypeList2) {
        this.attributeTypeList2 = attributeTypeList2;
    }

    public List<DisTaskAttribute> getAttributeTypeList3() {
        return attributeTypeList3;
    }

    public void setAttributeTypeList3(List<DisTaskAttribute> attributeTypeList3) {
        this.attributeTypeList3 = attributeTypeList3;
    }
}
