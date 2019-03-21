package com.example.demo.bean.others;

import com.example.demo.bean.po.CityPO;
import com.example.demo.dao.ICityDAO;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-16 下午4:51
 **/
public class DemoExecutorTask implements Callable<CityPO> {

    private int cityId;
    private ICityDAO cityDAO;
    private int sleepSec;

    public DemoExecutorTask(int cityId, ICityDAO cityDAO, int sleepSec) {
        this.cityId = cityId;
        this.cityDAO = cityDAO;
        this.sleepSec = sleepSec;
    }

    @Override
    public CityPO call() throws Exception {
        System.out.println("正在执行的是线程：" + Thread.currentThread().getName() +" ,我在获取城市: " + cityId);
        if (sleepSec == 2){
            TimeUnit.SECONDS.sleep(3);
        }else if (sleepSec == 3){
            TimeUnit.SECONDS.sleep(2);
        }
        CityPO cityPO = cityDAO.selectByProvinceId(cityId);
        System.out.println("我已经执行完了，我是线程：" + Thread.currentThread().getName());
        return cityPO;
    }
}
