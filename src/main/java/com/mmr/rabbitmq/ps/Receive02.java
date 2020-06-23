package com.mmr.rabbitmq.ps;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 订阅模式
 */
public class Receive02 {
    public static final  String QUEUE_NAME = "test_exchange_queue02";
    public static final String EXCHANGE_NAME = "test_exchange_fanout";


    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        final Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 队列绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
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
                System.out.println("[1] Receive Msg : "+msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done ");
                    // 反馈消息的被消费状态
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);



    }


}



