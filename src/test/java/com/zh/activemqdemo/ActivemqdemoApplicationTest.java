package com.zh.activemqdemo;

import com.zh.activemqdemo.boot_sit.QueueProduce;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = ActivemqdemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class ActivemqdemoApplicationTest {
    @Autowired
    private QueueProduce queueProduce;

    @Test
    public void testSend(){
        queueProduce.produceMsg();
    }

}