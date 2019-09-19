package com.atinbo.example;

import com.atinbo.rocketmq.annotation.MQConsumer;
import com.atinbo.rocketmq.base.AbstractMQPushConsumer;
import com.atinbo.rocketmq.base.MessageBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * 演示Demo
 *
 * @author breggor
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class ProducerApplicationDemo {

    @Value("${helloworld.topic}")
    private String topic;

    @Autowired
    CustomMQProducer customMQProducer;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplicationDemo.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send() {
        HelloWorldMsg msg = new HelloWorldMsg().setContent("hello world").setType("demo");
        customMQProducer.syncSend(MessageBuilder.of(msg).topic(topic).build());
    }


    @MQConsumer(topic = "${helloworld.topic}", consumerGroup = "${helloworld.consumer.group}")
    class CustomConsumer extends AbstractMQPushConsumer<HelloWorldMsg> {

        @Override
        public boolean process(HelloWorldMsg message, Map extMap) {
            // extMap 中包含messageExt中的属性和message.properties中的属性
            System.out.println(message);
            return true;
        }
    }
}