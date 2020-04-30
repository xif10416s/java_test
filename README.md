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