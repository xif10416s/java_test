package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import io.netty.channel.{ChannelFutureListener, ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TimeServerHandler extends ChannelInboundHandlerAdapter {
  // 连接成功，服务器端先发消息
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    val f = ctx.writeAndFlush(new UnixTime()) // (3)
    f.addListener(ChannelFutureListener.CLOSE) // (4)
  }
}
