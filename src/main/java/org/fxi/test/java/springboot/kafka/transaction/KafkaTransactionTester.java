package org.fxi.test.java.springboot.kafka.transaction;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka.transaction" , exclude = RedissonAutoConfiguration.class)
public class KafkaTransactionTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaTransactionTester.class);

    private final CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTransactionTester.class, args);
    }

    /**
     * 所有数据没有提交：
     * 2020-05-28 21:53:45.205 ERROR 3264 --- [-thread | ttt-2] o.s.k.support.LoggingProducerListener    : Exception thrown when sending a message with key='53' and payload='foo1' to topic test2:
     * org.apache.kafka.common.KafkaException: Failing batch since transaction was aborted
     *
     * 通过kafka tool查看 g1 消费情况：
     *  partition 0 end增加了一 ， 同时lag也增加了1
     *
     * 正常启动consumer g1 有lag数量大于1 但不消费数据，lag不变
     *
     *  过一段时间后，lag变为1
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        transactionWay1();
//        transactionWay2();

    }

    /**
     * 方式1：
     *   @Transactional
     *
     */
    @Transactional
    public void transactionWay1(){
        kafkaTemplate.send("test2", "31","foo1");
        throw new RuntimeException("fail");
    }


    /**
     * 方式二：
     * 通过kafkaTemplate.executeInTransaction启动事务，不需要注解
     */
    public void transactionWay2(){
        kafkaTemplate.executeInTransaction( t-> {

            ListenableFuture send = t.send("test2", "51", "foo1");
            t.send("test2", "52", "foo1");
            t.send("test2", "53", "foo1");
            throw new RuntimeException("fail");
            //return true;
        });
        logger.info("All received");
    }

    /**
     *  KafkaListener
     * @param cr
     * @throws Exception
     */
    @KafkaListener(id="demo02" , topics = "test2", concurrency="2" ,groupId = "g1")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }

}
