package com.example.demo.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-20 下午6:52
 **/

public class MultipleDataSource extends AbstractRoutingDataSource{
    @Nullable
    @Override
    /*
    决定选择哪个数据源的方法，可以看源码发现getConnection()中实际执行的是determineTargetDataSource().getConnection()，
    也就是说通过determineTargetDataSource()来确定数据源，而在determineTargetDataSource()方法中是通过determineCurrentLookupKey()
    方法来确定数据源的，而determineCurrentLookupKey()这个方法是个抽象方法，也就是我们在继承AbstractRoutingDataSource类时候需要实现的
    方法，也就是说我们自己来实际判断数据源的选择。
     */
    protected Object determineCurrentLookupKey() {

        double random = Math.random() * 10;
        int randomInt = new Double(random).intValue();
        System.out.println("测试多数据源..." + randomInt);
        if (randomInt % 2 == 0){
            System.out.println("我走的master");
            return "masterDataSource";
        }else {
            System.out.println("我走的slave");
            return "slaveDataSource";
        }
    }
}
