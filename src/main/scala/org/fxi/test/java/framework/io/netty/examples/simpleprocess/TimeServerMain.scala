package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * 时间服务简单实现
  * https://netty.io/wiki/user-guide-for-4.x.html
  *
  * @param port
  */
class TimeServerMain(port: Int) {

  import io.netty.bootstrap.ServerBootstrap
  import io.netty.channel.nio.NioEventLoopGroup
  import io.netty.channel.socket.nio.NioServerSocketChannel
  import io.netty.channel.{ChannelInitializer, ChannelOption}

  @throws[Exception]
  def run(): Unit = {
    val bossGroup = new NioEventLoopGroup
    // (1)
    val workerGroup = new NioEventLoopGroup
    try {
      val b = new ServerBootstrap // (2)
      b.group(bossGroup, workerGroup).channel(classOf[NioServerSocketChannel])
        .childHandler(new ChannelInitializer[SocketChannel]() {
          override def initChannel(c: SocketChannel): Unit = {
            c.pipeline().addLast(new TimeEncoder(),new TimeServerHandler())
          }
        }).option[Integer](ChannelOption.SO_BACKLOG, 128)
        .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true) // (6)

      // Bind and start to accept incoming connections.
      val f = b.bind(port).sync() // (7)
      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel.closeFuture.sync()
    } finally {
      workerGroup.shutdownGracefully()
      bossGroup.shutdownGracefully()
    }
  }
}


object TimeServerMain {
  def main(args: Array[String]): Unit = {
    new TimeServerMain(8086).run()
  }
}
