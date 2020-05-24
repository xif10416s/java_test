package org.fxi.test.java.springboot.redis.redisson;

import org.fxi.test.java.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * RedissonClient, RedissonReactiveClient and RedissonRxClient instances as well as Redisson objects are fully thread-safe.
 * Redisson supports auto-retry policy for each operation and tries to send command during each attempt. Retry policy controlled by retryAttempts (default is 3) and retryInterval (default is 1000 ms) settings. Each attempt executed after retryInterval time interval.
 * Redisson objects with synchronous/asynchronous methods could be reached via RedissonClient interface. Reactive and RxJava2 methods through RedissonReactiveClient and RedissonRxClient interfaces respectively.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Component
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedissonTester {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedissonReactiveClient redissonReactiveClient;

    /**
     * 同步方式调用
     */
    @Test
    public void demoSync(){
        RAtomicLong longObject = redissonClient.getAtomicLong("myLong");
        longObject.compareAndSet(3, 401);
    }

    /**
     * 异步方式调用
     */
    @Test
    public void demoASync(){
        long myLong = redissonClient.getAtomicLong("myLong").get();
        System.out.println(myLong);
        RAtomicLong longObject = redissonClient.getAtomicLong("myLong");
        RFuture<Boolean> result = longObject.compareAndSetAsync(1, 2);
        result.whenComplete((res,exp ) ->{
            System.out.println(res.booleanValue());
        });
    }

    /**
     * 响应式方式调用
     * TODO
     */
    @Test
    public void demoReactive(){
        RAtomicLongReactive ml = redissonReactiveClient.getAtomicLong("ml");
        ml.set(2).doOnSuccess( Void ->{
            System.out.println("--------------------");
            Mono<Long> get = ml.get();
            get.doOnSuccess( ll ->{
                System.out.println(ll);
            });
        });
    }

    /**
     * Java implementation of Redis based RBucket object is a holder for any type of object. Size is limited to 512Mb.
     */
    @Test
    public void demoDistributedObjects(){
        RBucket<TestBean> bucket = redissonClient.getBucket("test");

        bucket.set(new TestBean("john",1));
        TestBean obj = bucket.get();
        System.out.println(obj.getName());
    }


    /**
     *  Redis based distributed Map object for Java implements ConcurrentMap interface. Consider to use Live Object service to store POJO object as Redis Map.
     *  适合读取操作多，不必要网络传输的场景，有本地缓存支持s
     */
    @Test
    public void demoDistributedCollections(){
        RMap<String, TestBean> map = redissonClient.getMap("anyMap");
        TestBean prevObject = map.put("key1", new TestBean("123",1));
        TestBean currentObject = map.putIfAbsent("key1", new  TestBean("234",1));
        System.out.println(currentObject.getName());
    }


    /**
     *  RMap object allows to bind a Lock/ReadWriteLock/Semaphore/CountDownLatch object per key:
     */
    @Test
    public void demoDistributedCollectionsForLock(){
        RMap<String, TestBean> map = redissonClient.getMap("anyMap");
        TestBean k = new TestBean("123",1);
        RLock keyLock = map.getLock("key1");
        keyLock.lock();
        try {
            TestBean v = map.get(k);
            // process value ...
        } finally {
            keyLock.unlock();
        }

        RReadWriteLock rwLock = map.getReadWriteLock("key1");
        rwLock.readLock().lock();
        try {
            TestBean v = map.get(k);
            // process value ...
        } finally {
            rwLock.readLock().unlock();
        }
    }


    /**
     * 可重入锁：是最基本的锁，一旦上锁，其他的应用在未获得锁的时候，不能读也不能写
     * lock 中的两个参数
     * 第一个是锁的保持时间，超过这个时间，锁会自动释放，
     * 第二个是单位。如果第一个参数是-1 ，则必须等到执行锁释放，其他的操作才能拿到这个锁。
     * tryLock 第一参数是尝试时间，超过则放弃，后面两个参数与lock一样
     */
    @Test
    public void lock(){
        RLock rLock = redissonClient.getLock("lock");
        try {
            // rLock.lock(30, TimeUnit.SECONDS);
            rLock.tryLock(3,100, TimeUnit.SECONDS);
            System.out.println("get lock1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
    }

    /**
     * 公平锁: 保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程
     */
    @Test
    public void fairLock(){
        RLock fairLock = redissonClient.getFairLock("lock");
        try{
            fairLock.lock(10, TimeUnit.SECONDS);
            System.out.println("get fair lock");
        }finally {
            fairLock.unlock();
        }
    }

    /**
     * 按一组对象作为一个锁，每个锁属于不同的Redisson对象
     * 组合锁
     * @param redisson1
     * @param redisson2
     * @param redisson3
     */
    @Test
    public void testMultiLock(RedissonClient redisson1,RedissonClient redisson2, RedissonClient redisson3){
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 红锁
     * 分布式锁，将多个锁当成一个锁处理
     * @param redisson1
     * @param redisson2
     * @param redisson3
     */
    @Test
    public void testRedLock(RedissonClient redisson1,RedissonClient redisson2, RedissonClient redisson3){
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *  读写锁
     */
    @Test
    public void lockWriteLock(){
        RReadWriteLock rLock = redissonClient.getReadWriteLock("lock");
        try {
            rLock.writeLock().lock(30, TimeUnit.SECONDS);
            //  rLock.writeLock().lock();
            System.out.println("get write lock");
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.writeLock().unlock();
        }

    }
}

class TestBean implements Serializable {
    private String name;
    private int age;
    public TestBean(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
