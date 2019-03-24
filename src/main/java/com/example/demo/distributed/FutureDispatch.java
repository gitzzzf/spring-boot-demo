package com.example.demo.distributed;


import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-23 下午4:25
 **/
public class FutureDispatch extends FutureSetting{
    ExecutorService executorService;

    public FutureDispatch(int threadCount) {
        // 获取指定大小的线程池
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * 将方法执行的任务分派给不同的线程去做
     */
    public <T> Future<T> submit() {
        InvokeContext invokeContext = getInvokeContext();
        Method method = invokeContext.method;
        Object realObject = invokeContext.realObject;
        Object[] args = invokeContext.args;
        System.out.println("===========main线程为：" + Thread.currentThread().getName());
        Future<T> future = executorService.submit(() -> {
            System.out.println("===========执行dispatch的线程为：" + Thread.currentThread().getName());
            T result = (T) method.invoke(realObject, args);
            return result;
        });
        return future;
    }
}
