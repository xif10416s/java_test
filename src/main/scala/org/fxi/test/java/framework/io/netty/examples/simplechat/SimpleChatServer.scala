package org.fxi.test.java.framework.io.netty.examples.simplechat

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

object SimpleChatServer {
  def main(args: Array[String]): Unit = {
    val bossGroup = new NioEventLoopGroup()
    // (1)
    val workerGroup = new NioEventLoopGroup()
    try {
      val b = new ServerBootstrap() // (2)
      b.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(new SimpleChatServerInitializer)
        .option[java.lang.Integer](ChannelOption.SO_BACKLOG, 128)
        .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true) // (6)

      System.out.println("SimpleChatServer 启动了")
      // 绑定端口，开始接收进来的连接
      val f = b.bind(8087).sync() // (7)
      // 等待服务器  socket 关闭 。
      // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
      f.channel.closeFuture.sync
    } finally {
      workerGroup.shutdownGracefully()
      bossGroup.shutdownGracefully()
      System.out.println("SimpleChatServer 关闭了")
    }
  }

}
