package com.mmr.rabbitmq.routing;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 路由模式
 */
public class Send {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个交换机(分发模式)
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //发送消息并指明路由
        String msg = " error Hello direct ~";
        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());

        System.out.println("Send direct :"+msg);
        channel.close();
        connection.close();

    }
}




