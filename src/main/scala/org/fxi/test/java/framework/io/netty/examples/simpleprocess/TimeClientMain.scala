package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import io.netty.bootstrap.Bootstrap
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

object TimeClientMain {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8086
    val workerGroup = new NioEventLoopGroup()

    try { //BootStrap 和 ServerBootstrap 类似,不过他是对非服务端的 channel 而言，比如客户端或者无连接传输模式的 channel。
      val b = new Bootstrap() // (1)
      //如果你只指定了一个 EventLoopGroup，那他就会即作为一个 boss group ，也会作为一个 workder group，尽管客户端不需要使用到 boss worker 。
      b.group(workerGroup) // (2)

      //代替NioServerSocketChannel的是NioSocketChannel,这个类在客户端channel 被创建时使用。
      b.channel(classOf[NioSocketChannel]) // (3)

      b.option[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true) // (4)

      b.handler(new ChannelInitializer[SocketChannel]() {
        def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline.addLast(new TimeDecoder(), new TimeClientHandler() )
        }
      })
      // 启动客户端
      val f = b.connect(host, port).sync() // (5)
      // 等待连接关闭
      f.channel.closeFuture.sync()
      //
      //            ChannelFuture f2 = b.connect(host, port).sync(); // (5)
      //
      //            // 等待连接关闭
      //            f2.channel().closeFuture().sync();
    } finally workerGroup.shutdownGracefully()
  }
}
