package org.fxi.test.java.springboot.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissonBeanConfig {
    @Autowired
    RedissonClient redissonClient;

    @Bean
    RedissonReactiveClient initRedissonReactiveClient(){
        return Redisson.createReactive(redissonClient.getConfig());
    }
}
