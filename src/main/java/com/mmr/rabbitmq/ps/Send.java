package com.mmr.rabbitmq.ps;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 订阅模式 ps = publish
 * 生产者将消息发送到交换机
 */
public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个交换机(分发模式)
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        // 发送消息
        String msg = "afHello exchange ,Publish/Subscribe Mode !";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("------[send] msg :"+ msg);

        //释放资源
        channel.close();
        connection.close();

    }


}



