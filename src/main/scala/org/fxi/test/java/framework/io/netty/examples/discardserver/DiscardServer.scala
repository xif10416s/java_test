package org.fxi.test.java.framework.io.netty.examples.discardserver

import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * 简单的服务器交互
  * https://netty.io/wiki/user-guide-for-4.x.html
  *
  * @param port
  */
class DiscardServer(port: Int) {

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
            c.pipeline().addLast(new DiscardServerHandler())
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

class DiscardServerHandler extends ChannelInboundHandlerAdapter {
  // 当接受到客户端发来消息时调用
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    import io.netty.buffer.ByteBuf
    val in = msg.asInstanceOf[ByteBuf]

    while (in.isReadable()) { // (1)
      System.out.print(in.readByte.toChar)
      System.out.flush()
    }
    val buf = ctx.alloc().buffer(20)
    buf.writeBytes("hello from server".getBytes("UTF-8"))
    ctx.write(buf); // (1)
    ctx.flush(); // (2)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace
    ctx.close()
  }
}

object DiscardServer {
  def main(args: Array[String]): Unit = {
    new DiscardServer(8086).run()
  }
}
