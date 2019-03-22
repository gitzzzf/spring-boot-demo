package com.example.demo.dubbo;

import com.zzzf.demo.IDemoDubboService;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-22 上午11:52
 **/
/*
 * 这里可以使用的alibaba的@Service，和spring的@Service注解不同，参考如下：
 * https://blog.csdn.net/lkforce/article/details/52983345
 */
public class DemoDubboServiceImpl implements IDemoDubboService {
    @Override
    public String dubboTest(int param) {
        System.out.println("测试参数为:" + param);
        return "test success";
    }
}
