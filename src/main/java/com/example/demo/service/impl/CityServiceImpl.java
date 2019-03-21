package com.example.demo.service.impl;

import com.example.demo.aop.DemoAnnotation;
import com.example.demo.bean.model.CityModel;
import com.example.demo.bean.msg.CityMsg;
import com.example.demo.bean.others.DemoExecutorTask;
import com.example.demo.bean.po.CityPO;
import com.example.demo.constant.DemoConstant;
import com.example.demo.dao.ICityDAO;
import com.example.demo.mq.MqSender;
import com.example.demo.service.ICityService;
import com.example.demo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午6:06
 **/

@Service
public class CityServiceImpl implements ICityService {

    private final static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Resource
    private ICityDAO cityDAO;

    @Autowired
    @Qualifier("demoExecutor")
    private ThreadPoolTaskExecutor demoExecutor;

    @Resource(name = "mqMessageSender")
    private MqSender mqSender;

    @Override
    @DemoAnnotation
    @Transactional
    public CityModel getSimpleCity() {
        logger.info("===================info" + Math.random());
        DemoConstant.SPECIAL_LOGGER.info("=========special logger: " + Math.random());
        CityPO simpleCity = cityDAO.selectByProvinceId(1);
        CityModel cityModel = new CityModel()
                .setCityName(simpleCity.getCityName())
                .setDescription(simpleCity.getDescription());
        return cityModel;
    }

    @Override
    @DemoAnnotation(id = 2)
    public List<CityPO> getMoreCity() {
        System.out.println("正在执行的主线程： " + Thread.currentThread().getName());
        List<CityPO> list = new ArrayList<>();
        List<Future<CityPO>> futureList = new ArrayList<>();
        int[] cityArray = {2, 3};
        // 测试两个线程去哪各自的城市信息
        for (int cityId : cityArray) {
            DemoExecutorTask task = new DemoExecutorTask(cityId, cityDAO, cityId);
            Future<CityPO> future = demoExecutor.submit(task);
            futureList.add(future);
        }
        System.out.println("任务都已经提交了，他们还得睡一会儿，进行阻塞等待...");
        for (Future<CityPO> cityPOFuture : futureList) {
            try {
                list.add(cityPOFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void testCityMq() {
        CityMsg cityMsg = new CityMsg(DemoConstant.CITY_MQ_TYPE);
        cityMsg.setProvinceId(2);
        cityMsg.setCityName("shanghai");
        mqSender.sendMsg(cityMsg, "rk.city");
    }
}
