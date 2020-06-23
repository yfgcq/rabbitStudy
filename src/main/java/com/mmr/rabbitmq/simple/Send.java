package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 消息生产者
 */
public class Send {

    public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "gyf126 Hello Simple Queue !";

        //exchange routingKey props  body
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("------send msg :"+ msg);
        channel.close();
        connection.close();

    }

}


