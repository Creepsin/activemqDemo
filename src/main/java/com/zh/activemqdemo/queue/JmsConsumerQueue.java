package com.zh.activemqdemo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumerQueue {
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

        //5.创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /*
        同步阻塞
        while (true){
            //如果receive方法不带时间参数，那么就代表
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if (null != textMessage){
                System.out.println("******消费者接收到消息" + textMessage.getText());
            }else {
                break;
            }
        }

        //6.关闭连接
        messageConsumer.close();
        session.close();
        connection.close();*/

        //异步非阻塞（通过监听的方式来消费消息）
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("*******消费者接收到消息：" + textMessage.getText());
                    } catch (JMSException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();//保证控制台不灭
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
