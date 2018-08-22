package com.github.wxiaoqi.security.xjsystem.config;

/**
 * @author chengyuan
 * @create 2018-08-22 10:54
 * @desc
 **/
import com.github.wxiaoqi.security.xjsystem.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }
    /**
     * 创建人：张博
     * 时间：2018/3/5 上午10:45
     * @apiNote 定义扇出（广播）交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }

    /**
     * 创建人：张博
     * 时间：2018/3/5 上午10:48
     * @apiNote 定义自动删除匿名队列
     */
    @Bean
    public Queue queue() {
        return new Queue("queueTwo");
//        return new AnonymousQueue();
    }

    /**
     * 创建人：张博
     * 时间：2018/3/5 上午10:48
     * @apiNote 定义自动删除匿名队列
     */
    @Bean
    public Queue queue2() {
        return new Queue("queueThree");
//        return new AnonymousQueue();
    }

    /**
     * 创建人：张博
     * 时间：2018/3/5 上午10:48
     * @param fanoutExchange 扇出（广播）交换器
     * @param queue 自动删除队列
     * @apiNote 把队列绑定到扇出（广播）交换器
     * @return Binding
     */
    @Bean
    public Binding binding0(FanoutExchange fanoutExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 创建人：张博
     * 时间：2018/3/5 上午10:55
     * @param fanoutExchange 扇出（广播）交换器
     * @param queue2 自动删除队列
     * @apiNote 把队列绑定到扇出（广播）交换器
     * @return Binding
     */
    @Bean
    public Binding binding1(FanoutExchange fanoutExchange, Queue queue2) {
        System.out.println("queue++++++++++:" + queue2.getName());
        return BindingBuilder.bind(queue2).to(fanoutExchange);
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("receive msg b: " + new String(body));
//                Thread.sleep(1000);
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//
//            }
//        });
//        return container;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageContainer2() throws Exception{
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue2());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("receive msg a: " + new String(body));
//                Thread.sleep(1000);
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//            }
//        });
//        return container;
//    }
}
