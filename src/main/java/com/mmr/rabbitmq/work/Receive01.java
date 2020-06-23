package com.mmr.rabbitmq.work;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 工作队列 消费者1获得消息
 */
public class Receive01 {

    public static final  String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            // 获取消息的回调函数
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[1] Receive Msg : "+msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done ");
                }

            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }
}


