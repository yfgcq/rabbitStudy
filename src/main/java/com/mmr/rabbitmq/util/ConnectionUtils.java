package com.mmr.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * 获取 MQ 连接的工具类
 */
public class ConnectionUtils {


    public static Connection getConnection() throws IOException {
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //设置 AMQP协议 端口
        factory.setPort(5672);
        //设置账号信息、用户名、密码、vhost
        factory.setVirtualHost("vhost_gyf");
        factory.setUsername("gyf");
        factory.setPassword("gyf123");
        //通过工程获取连接
        Connection connection = factory.newConnection();

        return connection;
    }
}


