package com.zh.activemqdemo.embed;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {
    public static void main(String[] args) {
        try {
            //ActiveMQ也支持在vm通信基于嵌入式的broker
            BrokerService brokerService = new BrokerService();
            brokerService.setUseJmx(true);
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
