package com.zh.activemqdemo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceQueueTX {
    public static final String ACTIVEMQ_URL = "tcp://39.106.85.90:61616";
    public static final String ACTIVEMQ_USERNAME = "admin";
    public static final String ACTIVEMQ_PASSWORD = "admin";
    public static final String QUEUE_NAME = "queue_TX";

    public static void main(String[] args) throws Exception{
        //1.创建连接工厂，按照给定的URL地址，采用默认的用户名个密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_USERNAME, ACTIVEMQ_PASSWORD, ACTIVEMQ_URL);

        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session,第一个参数是事物，第二个参数是签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //将设置消息持久化，队列的默认传送模式是持久化模式（可靠，不会丢失）
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6.通过使用MessageProducer生产3条消息发送到MQ的队列中
        for (int i = 0; i < 3; i++){
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg-TX----" + i);
            //8.通过MessageProducer发送到MQ
            messageProducer.send(textMessage);
        }
        //9.逆序关闭资源
        messageProducer.close();
        //事物设为true，那么需要在session关闭之前进行session的commit操作，这样消息才会真正进入到队列中
        session.commit();
        session.close();
        connection.close();

        System.out.println("****TX消息已发送到MQ");

      /*  //事物一般操作
        try {
            //OK
            session.commit();
        } catch (JMSException e) {
            //error
            session.rollback();
            e.printStackTrace();
        } finally {
            if (session != null){
                session.close();
            }
        }*/
    }
}
