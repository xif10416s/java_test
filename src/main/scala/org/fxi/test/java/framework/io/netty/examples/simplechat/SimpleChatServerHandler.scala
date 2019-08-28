package org.fxi.test.java.framework.io.netty.examples.simplechat

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor

class SimpleChatServerHandler extends SimpleChannelInboundHandler[String]{
  var channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    val incoming = ctx.channel

    // Broadcast a message to multiple Channels
    channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress + " 加入\n")

    channels.add(ctx.channel)
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    val incoming = ctx.channel

    // Broadcast a message to multiple Channels
    channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress + " 离开\n")

    // A closed Channel is automatically removed from ChannelGroup,
    // so there is no need to do "channels.remove(ctx.channel());"
  }

  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    val incoming = ctx.channel
    import scala.collection.JavaConversions._
    for (channel <- channels) {
      if (channel != incoming)
        channel.writeAndFlush("[" + incoming.remoteAddress + "]" + msg + "\n")
      else channel.writeAndFlush("[you]" + msg + "\n")
    }
  }

  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    val incoming = ctx.channel
    System.out.println("SimpleChatClient:" + incoming.remoteAddress + "在线")
  }

  override def channelInactive(ctx: ChannelHandlerContext): Unit = {
    val incoming = ctx.channel
    System.out.println("SimpleChatClient:" + incoming.remoteAddress + "掉线")
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    val incoming = ctx.channel
    System.out.println("SimpleChatClient:" + incoming.remoteAddress + "异常")
    // 当出现异常就关闭连接
    cause.printStackTrace()
    ctx.close
  }
}
