package org.fxi.test.java.springboot.redis.redisson;

import org.fxi.test.java.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 *  RedisTemplate默认只有StringRedisTemplate实现，可以自己实现 string ， object
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTester {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testGet(){
        stringRedisTemplate.opsForValue().set("a","123");
        String a = stringRedisTemplate.opsForValue().get("a");
        System.out.println(a);
        Assert.assertEquals(a,"123");
    }
}
