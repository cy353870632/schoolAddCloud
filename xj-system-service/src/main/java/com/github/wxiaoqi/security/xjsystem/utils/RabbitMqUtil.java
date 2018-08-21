package com.github.wxiaoqi.security.xjsystem.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author chengyuan
 * @create 2018-08-20 14:43
 * @desc 消息队列工具
 **/
public class RabbitMqUtil {


    public static Connection getConnection() throws  Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
//        factory.setVirtualHost("vhostOne");
        return factory.newConnection();
    }

}
