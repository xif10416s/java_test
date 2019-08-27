package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TimeClientHandler extends ChannelInboundHandlerAdapter{
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    val m = msg.asInstanceOf[UnixTime]
    System.out.println(m + "  " + Thread.currentThread)
    ctx.close
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
    ctx.close()
  }
}
