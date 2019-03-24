package com.example.demo.distributed;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-23 下午4:13
 **/
public class DemoFutureDispatch extends FutureDispatch{

    // 这个分派器里面线程的数量
    public static final int threadCount = 3;

    public DemoFutureDispatch() {
        super(threadCount);
    }
}
