package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.service.IProviceService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.RabbitMqUtil;
import com.github.wxiaoqi.security.xjsystem.vo.ProvinceVo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/Rabbit")
@Api(tags = {""},description = "")
@Slf4j
public class RabbitMqController extends BaseController{


    @RequestMapping(value = "passSchool", method = RequestMethod.POST)
    public void passSchool(HttpServletRequest request,String id) throws Exception {
        Connection connection =  RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        String queueName = "queueOne";
        String exchangeName = "exchangerOne";
        String routingKey = "queueOne";
        channel.exchangeDeclare(exchangeName,"direct");
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        int msgCnt =4;
        while(msgCnt-->0){
            String msg = "测试第    "+msgCnt+"    次";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());  //发送消息
            System.out.println("produce msg :"+msg);
            Thread.sleep(1200);
//            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*500));
        }
        channel.close();
        connection.close();
    }

    @RequestMapping(value = "nopassSchool", method = RequestMethod.POST)
    public void nopassSchool(HttpServletRequest request,String id) throws Exception {
        Connection connection =  RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
//        String exchangeName = "fanout-exchange";
//        channel.exchangeDeclare(exchangeName,"fanout");
        int msgCnt =4;
        while(msgCnt-->0){
            String msg = "测试第    "+msgCnt+"    次";
            channel.basicPublish("fanout-exchange","",null,msg.getBytes());  //发送消息
            System.out.println("produce msg :"+msg);
            Thread.sleep(1200);
//            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*500));
        }
        channel.close();
        connection.close();
    }

    @RequestMapping(value = "rabit", method = RequestMethod.POST)
    public void rabit(HttpServletRequest request) throws Exception {

    }


}
