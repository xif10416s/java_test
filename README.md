#  java测试

##  基础数据结构
### 布隆过滤器 -- data.structure.bloomfilter
*   使用场景
    *   Google著名的分布式数据库Bigtable以及Hbase使用了布隆过滤器来查找不存在的行或列，以及减少磁盘查找的IO次数
    *   文档存储检查系统也采用布隆过滤器来检测先前存储的数据
    *   Goole Chrome浏览器使用了布隆过滤器加速安全浏览服务
    *   垃圾邮件地址过滤
    *   爬虫URL地址去重
    *   解决缓存穿透问题

*   CusBloomFilter -- 自定义bloomfilter实现

##  基础框架测试
###  netty -- framework.io.netty
*   DiscardServer -- 简单的服务器交互
*   simpleprocess -- 简单处理流程，包含一些基本处理
    *   服务器端接受到客户端连接后把时间编码后发送
    *   客户端接受消息，解码还原时间后显示
    *   编码、解码主要是为了防止拆包粘包，保证数据读取正确，每次都是4个字节
*   simplechat -- 简单聊天服务实现


##  主流框架spring 集成
### redis 集成
####  redisson集成测试
*   测试redis集群准备： docker pull 1nj0zren.mirror.aliyuncs.com/grokzen/redis-cluster
*   初始化配置
    *   org.redisson.spring.starter.RedissonAutoConfiguration
        *   redisTemplate,StringRedisTemplate,RedissonClient 
            *   默认没有初始化响应式客户端
            *   org.fxi.test.java.springboot.redis.redisson.RedissonBeanConfig 手动初始化一下
*   org.fxi.test.java.springboot.redis.redisson.RedissonTester -- 基础测试类
    *   单节点模式/集群模式配置
    *   https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95 参照配置
    *   测试案例
        *   基础用法
        *   锁 --https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers
*   org.fxi.test.java.springboot.redis.redisson.RedisTemplateTester

####  spring_kafka (kafka2.5)集成测试
*   测试环境准备：本地kafka2.5源码启动 ||  docker kafka ||  直接下载kafka本地启动
*   org.fxi.test.java.springboot.kafka.KafkaBasicTester : 基础 producer 与 consumer测试
    *   问题：kafkaListener 的concurrency 数量配置大于 topic的partition的时候实际还是启动了concurrency数量的consumer
*   org.fxi.test.java.springboot.kafka.KafkaTransactionTester : kafka事务测试，只需要配置producer的transactionIdPrefix，开启事务功能