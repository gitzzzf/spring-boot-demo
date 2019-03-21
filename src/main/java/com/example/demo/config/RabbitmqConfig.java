package com.example.demo.config;

import com.example.demo.mq.MqSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * 管理界面：
 * http://localhost:15672/#/
 *
 * @author zhoufeng
 * @date 2019-03-18 下午8:41
 **/

@Configuration
public class RabbitmqConfig {
    public static final String EXCHANGE_NAME = "exchange.main";
    public static final String QUEUE_DEMO_CITY = "queue.demo.city";
    public static final String RK = "rk.city";

    /**
     * ####################[下面构建mq发送方bean]############################
     */

    /**
     * mq发送线程池
     * @return
     */
    @Bean(name = "mqSenderExecutor", initMethod = "initialize", destroyMethod = "shutdown")
    @ConfigurationProperties("spring.demoexecutor")
    public ThreadPoolTaskExecutor mqSenderExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        return executor;
    }

    /**
     * 连接工厂
     * @return
     */
    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    /**
     * RabbitTemplate
     * @param connectionFactory
     * @return
     */
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setExchange("exchange.main");
        return rabbitTemplate;
    }

    /**
     * mq发送方
     * @param mqSenderExecutor
     * @param rabbitTemplate
     * @return
     */
    @Bean(name = "mqMessageSender")
    public MqSender mqSender(@Qualifier("mqSenderExecutor") ThreadPoolTaskExecutor mqSenderExecutor,
                             @Qualifier("rabbitTemplate") RabbitTemplate rabbitTemplate){
        MqSender mqSender = new MqSender();
        mqSender.setMqSenderExecutor(mqSenderExecutor);
        mqSender.setAmqpTemplate(rabbitTemplate);
        // 指定日志
        mqSender.setLoggerName("mqSender");
        return mqSender;
    }

    /**
     * demo监听city的队列
     * @return
     */
    @Bean(name = QUEUE_DEMO_CITY)
    public Queue queue(){
        Queue queue = new Queue(QUEUE_DEMO_CITY);
        return queue;
    }

    /**
     * 构建exchange
     * @return
     */
    @Bean(name = EXCHANGE_NAME)
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding binding(@Qualifier(QUEUE_DEMO_CITY) Queue queue,
                           @Qualifier(EXCHANGE_NAME) TopicExchange exchange){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(RK);
        return binding;
    }
}
