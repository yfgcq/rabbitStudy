package com.mmr.rabbitmq.confirm;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * confirm 消费者
 */
public class Receive01 {
    public static  final String QUEUE_NAME = "test_queue_confirm02";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");

                System.out.println("------[ confirm Receive] msg :"+msg);
            }
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);


    }

}








