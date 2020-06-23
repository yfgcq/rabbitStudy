package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 工作队列 消费者2获得消息
 */
public class Receive02 {

    public static final  String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        final Channel channel = connection.createChannel();
        // 声明一个队列
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        /**
         * 保证队列一次只分发一个消息
         */
        channel.basicQos(1);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            // 获取消息的回调函数
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2] Receive Msg : "+msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done ");
                    // 手动应答
                    channel.basicAck(envelope.getDeliveryTag(), false);

                }

            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }
}

