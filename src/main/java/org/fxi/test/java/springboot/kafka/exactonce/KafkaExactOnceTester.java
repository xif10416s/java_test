package org.fxi.test.java.springboot.kafka.exactonce;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka.exactonce" , exclude = RedissonAutoConfiguration.class)
public class KafkaExactOnceTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaExactOnceTester.class);

    private final CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaExactOnceTester.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.kafkaTemplate.send("test2", "1","foo1");
        this.kafkaTemplate.send("test2", "2","foo2");
        this.kafkaTemplate.send("test2", "3","foo3");
        this.kafkaTemplate.send("test2", "4","foo4");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    /**
     *  KafkaListener
     * @param cr
     * @throws Exception
     */
    @KafkaListener(id="demo03" , topics = "test", concurrency="4" ,groupId = "g1")
    public void listen(ConsumerRecord<?, ?> cr, Acknowledgment ack , Consumer consumer) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }





}
