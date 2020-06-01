package org.fxi.test.java.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicProducerTester {

    @Test
    public void testSend(){
        Producer<String, String> producer = createProducer();
        producer.send(new ProducerRecord<String, String>("test",
                "id-1", "foo1"));
    }

    /**
     * 发送成功后异步回调官方案例 （不阻塞）
     *  不要在回调中做耗时操作，占用producer io 线程
     */
    @Test
    public void testSendCallBack(){
        Producer<String, String> producer = createProducer();
        producer.send(new ProducerRecord<String, String>("test",
                "id-1", "foo1"),new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if(e != null) {
                            e.printStackTrace();
                        } else {
                            System.out.println("The offset of the record we just sent is: " + metadata.offset());
                        }
                    }
                });
    }

    /**
     * 同步调用，阻塞
     */
    @Test
    public void testSendSync(){
        Producer<String, String> producer = createProducer();
        try {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, String>("test",
                    "id-1", "foo1")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     *  开启事务
     */
    @Test
    public void testSendTrans(){
        Producer<String, String> producer = createProducer();
        producer.initTransactions();
        producer.beginTransaction();
        producer.send(new ProducerRecord<String, String>("test",
                    "id-1", "foo1"));
        producer.commitTransaction();
    }


    private static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
}
