package com.example.demo.distributed;

import java.lang.reflect.Method;

/**
 * 存储被代理的委托类的一些基础信息
 *
 * @author zhoufeng
 * @date 2019-03-23 上午11:22
 **/
public class InvokeContext {
    Object realObject;
    Class objectClazz;
    Method method;
    Object[] args;


    public void setObject(Object object) {
        this.realObject = object;
        if (object != null){
            this.objectClazz = object.getClass();
        }
    }
}
