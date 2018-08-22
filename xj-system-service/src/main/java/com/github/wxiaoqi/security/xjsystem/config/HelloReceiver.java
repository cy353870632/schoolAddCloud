package com.github.wxiaoqi.security.xjsystem.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author chengyuan
 * @create 2018-08-20 17:02
 * @desc
 **/
@Component
public class HelloReceiver {

//    @RabbitListener(queues="queueOne")    //监听器监听指定的Queue
//    public void processC(byte[] str) throws Exception {
//        System.out.println("Receive:"+new String(str,"UTF-8"));
//
//    }

//    @RabbitListener(queues = "queueOne")
//    public void process1(@Payload byte[] message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)throws Exception {
//        String mess = new String(message,"UTF-8");
////        if(mess.equals("测试第    0    次")){
////            //手动返回ack，确认，false是单个确认，true是全部确认
////            channel.basicAck(deliveryTag,false);
////        }
//        System.out.println("queueOne"+"   接收到"+mess);
//    }


//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queueTwo", durable = "true"),
//            exchange = @Exchange(value = "myExchange", type = ExchangeTypes.FANOUT)
//    ))
//    public void process2(@Payload byte[] message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)throws Exception {
//        String mess = new String(message,"UTF-8");
////        if(mess.equals("测试第    0    次")){
////            //手动返回ack，确认，false是单个确认，true是全部确认
////            channel.basicAck(deliveryTag,false);
////        }
//        System.out.println("queueTwo"+"   接收到"+mess);
//    }
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("queueThree"),
//            exchange = @Exchange("myExchange")
//    ))
//    public void process3(@Payload byte[] message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)throws Exception {
//        String mess = new String(message,"UTF-8");
////        if(mess.equals("测试第    0    次")){
////            //手动返回ack，确认，false是单个确认，true是全部确认
////            channel.basicAck(deliveryTag,false);
////        }
//        System.out.println("queueThree"+"   接收到"+mess);
//    }
}
