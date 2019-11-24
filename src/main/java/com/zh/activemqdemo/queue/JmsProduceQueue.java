package com.zh.activemqdemo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceQueue {
    //public static final String ACTIVEMQ_URL = "tcp://39.106.85.90:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String ACTIVEMQ_USERNAME = "admin";
    public static final String ACTIVEMQ_PASSWORD = "admin";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception{
        //1.创建连接工厂，按照给定的URL地址，采用默认的用户名个密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_USERNAME, ACTIVEMQ_PASSWORD, ACTIVEMQ_URL);

        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session,第一个参数是事物，第二个参数是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //将设置消息持久化，队列的默认传送模式是持久化模式（可靠，不会丢失）
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6.通过使用MessageProducer生产3条消息发送到MQ的队列中
        for (int i = 0; i < 3; i++){
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg----" + i);
            //8.通过MessageProducer发送到MQ
            messageProducer.send(textMessage);
        }
        //9.逆序关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("****消息已发送到MQ");
    }
}
