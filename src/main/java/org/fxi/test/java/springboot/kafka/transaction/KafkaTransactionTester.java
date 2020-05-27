package org.fxi.test.java.springboot.kafka.transaction;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka" , exclude = RedissonAutoConfiguration.class)
public class KafkaTransactionTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaTransactionTester.class);

    private final CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTransactionTester.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        kafkaTemplate.send("test", "1","foo1");

//        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
//            @Override
//            public Object doInOperations(KafkaOperations kafkaOperations) {
//                kafkaOperations.send("test", "1","foo1");
//                throw new RuntimeException("fail");
//                //return true;
//            }
//        });
        logger.info("All received");
        throw new RuntimeException("fail");
    }

    /**
     *  KafkaListener
     * @param cr
     * @throws Exception
     */
    @KafkaListener(id="demo02" , topics = "test", concurrency="4" ,groupId = "g1")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }





}
