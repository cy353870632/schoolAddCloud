package com.github.wxiaoqi.security.xjsystem.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 张博
 */
@Component
public class Receiver {

    @RabbitListener(queues = "queueTwo")
    public void receiver0(byte[] str) throws Exception{
        System.out.println("receiver0++++++++++:" + new String(str,"UTF-8"));
    }

    @RabbitListener(queues = "queueThree")
    public void receiver1(byte[] str) throws Exception{
        System.out.println("receiver1++++++++++:" + new String(str,"UTF-8"));
    }
}
