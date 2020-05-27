package org.fxi.test.java.springboot.kafka.basic;

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
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka.basic" , exclude = RedissonAutoConfiguration.class)
public class KafkaBasicTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaBasicTester.class);

    private final CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaBasicTester.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.kafkaTemplate.send("test2", "1","foo1");
        this.kafkaTemplate.send("test2", "2","foo2");
        this.kafkaTemplate.send("test2", "3","foo3");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    /**
     *  KafkaListener
     * @param cr
     * @throws Exception
     */
    @KafkaListener(id="demo01" , topics = "test", concurrency="4" ,groupId = "g1" )
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }


    /**
     *  KafkaListener
     * @throws Exception
     */
    @KafkaListener(id="demo11" , topics = "test2", concurrency="4" ,groupId = "g2")
    public void listen2(@Payload String data,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) throws Exception {

        logger.info("topic.quick.anno receive : \n"+
                "data : "+data+"\n"+
                "key : "+key+"\n"+
                "partitionId : "+partition+"\n"+
                "topic : "+topic+"\n"+
                "timestamp : "+ts+"\n"
        );
        latch.countDown();
    }

}
