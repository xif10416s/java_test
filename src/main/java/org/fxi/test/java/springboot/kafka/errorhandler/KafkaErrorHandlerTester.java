package org.fxi.test.java.springboot.kafka.errorhandler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import java.util.concurrent.CountDownLatch;

/**
 * https://www.confluent.io/blog/spring-for-apache-kafka-deep-dive-part-1-error-handling-message-conversion-transaction-support/
 */
@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka.errorhandler" , exclude = RedissonAutoConfiguration.class)
public class KafkaErrorHandlerTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaErrorHandlerTester.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    /**
     * acks = -1
     */
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaErrorHandlerTester.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Object o = kafkaTemplate.executeInTransaction(t -> {
//            t.send("test2", "51", "foo1");
//            t.send("test2", "52", "foo2");
//            t.send("test2", "53", "foo3");
//            return true;
//        });
//        System.out.println(o);
        logger.info("All received");
    }

    /**
     *  KafkaListener
     * @param cr
     * @throws Exception
     */
    @KafkaListener(id="demo03" , topics = "test2", concurrency="4" ,groupId = "g3")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
//        throw new RuntimeException("failed");
    }

    /**
     * 出错队列
     * @param in
     */
    @KafkaListener(id = "dltGroup", topics = "test2.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: " + in);
    }


    /**
     * 出错处理配置，publishes a failed record to a dead-letter topic
     * @param configurer
     * @param kafkaConsumerFactory
     * @param template
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory,
            KafkaTemplate<Object, Object> template) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template)));
        return factory;
    }


}
