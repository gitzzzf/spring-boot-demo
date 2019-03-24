package com.example.demo.distributed;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-23 下午4:28
 **/
public class FutureSetting {
    public static ConcurrentHashMap<Class, Object> proxyCache = new ConcurrentHashMap<>();

    public static ThreadLocal<InvokeContext> invokeContextThreadLocal = new ThreadLocal<>();

    /**
     * bean()方法里面要做两件事：
     * （1）初始化InvokeContext
     * （2）获取代理类
     */
    public <T> T bean(T object) {
        // 1.初始化InvokeContext上下文
        InvokeContext invokeContext = getInvokeContext();
        invokeContext.setObject(object);
        // 2.获取代理类
        Object proxy = proxyCache.get(object.getClass()); // 做个缓存，提高一些性能，不用每次都重新构建一个代理类
        if (proxy == null){
            proxy = getJdkProxy(object);
            proxyCache.put(proxy.getClass(), proxy);
        }
        return (T) proxy;

    }

    /**
     * 获取InvokeContext，把它方到ThreadLocal里面，因为线程其他地方用
     */
    public static InvokeContext getInvokeContext() {
        InvokeContext invokeContext = invokeContextThreadLocal.get();
        if (invokeContext == null){
            invokeContext = new InvokeContext();
            invokeContextThreadLocal.set(invokeContext);
        }
        return invokeContext;
    }

    /**
     * 获取jdk proxy
     */
    private static Object getJdkProxy(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new SettingInvokeContextHandler());
    }

    /**
     * 真实处理委托类调用的方法的handler
     */
    private static class SettingInvokeContextHandler implements InvocationHandler {

        // 这个invoke方法里面其实只做了初始化InvokeContext里面的method和args这两个属性这件事
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            InvokeContext invokeContext = getInvokeContext();
            invokeContext.method = method;
            invokeContext.args = args;
            return null;
        }
    }
}
