package com.example.demo.mq;

import com.example.demo.bean.msg.BaseMsg;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 上午1:16
 **/
public class MqSender extends MqMessageSender{

    @Override
    public void validateMessage(Object msg) {
        if (msg instanceof BaseMsg){
            ((BaseMsg)msg).validate();
        }else {
            throw new RuntimeException(String.format("nonsupport type: %s", msg.getClass().getName()));
        }
    }
}
