#  java测试

##  基础框架测试
###  netty -- framework.io.netty
*   DiscardServer -- 简单的服务器交互
*   simpleprocess -- 简单处理流程，包含一些基本处理
    *   服务器端接受到客户端连接后把时间编码后发送
    *   客户端接受消息，解码还原时间后显示
    *   编码、解码主要是为了防止拆包粘包，保证数据读取正确，每次都是4个字节
*   simplechat -- 简单聊天服务实现