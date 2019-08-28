package org.fxi.test.java.framework.io.netty.examples.simplechat

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

class SimpleChatClientHandler extends SimpleChannelInboundHandler[String] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(msg)
  }
}
