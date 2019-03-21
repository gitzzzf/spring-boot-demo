package com.example.demo.listener;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.msg.BaseMsg;
import com.example.demo.config.RabbitmqConfig;
import com.example.demo.util.JsonUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 下午3:27
 **/

@Component
public class DemoCityListener {

    /**
     * 该方法处理指定的队列
     * 注：RabbitListener注解：指定目标方法来作为消费消息的方法，通过注解参数指定所监听的队列或者Binding
     */
    @RabbitListener(queues = RabbitmqConfig.QUEUE_DEMO_CITY)
    protected void processMessage(Message message, Channel channel) throws Exception{
        boolean processResult = false;
        try {
            // 实际处理队列中的消息
            processResult = handleMessage(message);
        }catch (Exception e){
            throw e;
        }finally {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            if (processResult){
                // 成功消费. 注意第二个参数应该为false
//                channel.basicAck(deliveryTag, false);
            }else {
                // 不成功, 丢到dead letter (second param true:requeue false:dead letter)
//                channel.basicReject(deliveryTag, false);
            }
        }
    }

    /**
     * 实际处理消息的地方
     * @param message
     * @return
     */
    private boolean handleMessage(Message message) {
        String body = new String(message.getBody());
        BaseMsg baseMsg = JsonUtil.fromJson(body, BaseMsg.class);
        baseMsg.validate();
        System.out.println(String.format("我已经接到消息了: type: [%s]", baseMsg.getType()));
        JSONObject jsonObject = JSONObject.parseObject(body);
        jsonObject.getString("provinceId");
        System.out.println("消息内容为：" + jsonObject.getString("provinceId") + ", " + jsonObject.getString("cityName"));
        return true;
    }
}