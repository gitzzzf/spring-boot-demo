package com.example.demo.mq;

import com.example.demo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.core.task.TaskExecutor;

import java.util.Date;
import java.util.UUID;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 上午1:17
 **/
public abstract class MqMessageSender {
    private AmqpTemplate amqpTemplate;
    private TaskExecutor mqSenderExecutor;
    private String loggerName = "";

    /**
     * 消息验证
     * @param msg
     */
    public abstract void validateMessage(Object msg);

    /**
     * 消息发送入口
     * @param msg
     * @param rk
     */
    public void sendMsg(Object msg, String rk){
        // 消息验证
        validateMessage(msg);
        // 记录日志
        log(msg, rk);
        // 构建message
        Message message = generateMessage(msg);
        // 构建消息任务
        SendMessageTask sendMessageTask = new SendMessageTask(rk, message);
        // 消息发送
        mqSenderExecutor.execute(sendMessageTask);
    }

    /**
     * 发送消息任务
     */
    private class SendMessageTask implements Runnable{
        private String rk;
        private Message message;

        public SendMessageTask(String rk, Message message) {
            this.rk = rk;
            this.message = message;
        }

        @Override
        public void run() {
            amqpTemplate.convertAndSend(rk, message);
        }
    }

    /**
     * 构建消息
     * @param msg
     * @return
     */
    private Message generateMessage(Object msg) {
        String messageBody = JsonUtil.toJson(msg);

        Message message = MessageBuilder.withBody(messageBody.getBytes())
                .setMessageId(UUID.randomUUID().toString())
                .setTimestamp(new Date())
                .build();
        return message;
    }

    /**
     * 记录日志
     * @param msg
     * @param rk
     */
    private void log(Object msg, String rk) {
        Logger logger = LoggerFactory.getLogger(loggerName);
        logger.info(String.format("message info, rk: %s, body: %s", rk, msg.toString()));
    }

    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public MqMessageSender setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
        return this;
    }

    public TaskExecutor getMqSenderExecutor() {
        return mqSenderExecutor;
    }

    public MqMessageSender setMqSenderExecutor(TaskExecutor mqSenderExecutor) {
        this.mqSenderExecutor = mqSenderExecutor;
        return this;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public MqMessageSender setLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }
}
