package org.fxi.test.java.springboot.redis.lettuce;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.fxi.test.java.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Component
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LettuceTester {
    /**
     * 同步方式调用
     */
    @Test
    public void demoCluster(){
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://192.168.99.10:27002");

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }

}
