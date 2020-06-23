package com.mmr.rabbitmq.confirm;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 消息确认机制 confirm
 */
public class Send {
    public static  final String QUEUE_NAME = "test_queue_confirm02";
    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        // QUEUE_NAME durable
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        String msg = "Hello confirm Queue !";

        //指定消息的投递模式 :confirm 确认模式
        channel.confirmSelect();

        for (int i = 0; i<10; i++){
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("[Send]  Msg  "+i);
        }

        //添加一个确认监听， 这里就不关闭连接了，为了能保证能收到监听消息
        channel.addConfirmListener(new ConfirmListener() {

            // Ack成功的回调函数
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("Success Ack !");
                    System.out.println("multiple : "+multiple);

                }else{
                    System.out.println("Success Ack !");
                    System.out.println("multiple : "+multiple);
                }

            }

            //Ack失败的回调函数
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("Failed Ack !");
                    System.out.println("multiple : "+multiple);
                }else{
                    System.out.println("Failed Ack !");
                    System.out.println("multiple : "+multiple);
                }
            }
        });



    }
}






