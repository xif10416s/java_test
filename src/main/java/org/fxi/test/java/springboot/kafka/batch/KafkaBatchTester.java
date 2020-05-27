package org.fxi.test.java.springboot.kafka.batch;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot.kafka.batch" , exclude = RedissonAutoConfiguration.class)
public class KafkaBatchTester implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(KafkaBatchTester.class);

    private final CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaBatchTester.class, args);
    }

    @Override
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
    @KafkaListener(id="demo01" , topics = "test2", concurrency="2", containerFactory = "batchContainerFactory" ,groupId = "g1" ,properties={ "max.poll.interval.ms:1000",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=2"})
    public void listen(List<String> cr) throws Exception {
        // TODO 多线程运行
        logger.info(cr.size() +"");
        latch.countDown();
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        //一次拉取消息数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean("batchContainerFactory")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        //设置为批量监听
        container.setBatchListener(true);
        return container;
    }
    
}
