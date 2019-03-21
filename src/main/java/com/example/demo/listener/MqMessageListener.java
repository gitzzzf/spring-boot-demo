package com.example.demo.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.Date;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 下午3:25
 **/
public abstract class MqMessageListener implements ChannelAwareMessageListener{

    /**
     * 模板方法, 返回false会把消息重新放回队列
     * @param message
     * @return
     */
    protected abstract boolean processMessage(Message message);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        boolean processResult = false;
        try {
            processResult = processMessage(message);
        }catch (Exception e){
            throw e;
        }finally {
            if (processResult){
                // 成功消费. 注意第二个参数应该为false
                channel.basicAck(deliveryTag, false);
            }else {
                // 不成功, 丢到dead letter (second param true:requeue false:dead letter)
                channel.basicReject(deliveryTag, false);
            }
        }
    }
}
