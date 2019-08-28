package org.fxi.test.java.framework.io.netty.examples.simplechat

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}
import io.netty.handler.codec.{DelimiterBasedFrameDecoder, Delimiters}

class SimpleChatClientInitializer extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline = ch.pipeline
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter():_*))
    pipeline.addLast("decoder", new StringDecoder())
    pipeline.addLast("encoder", new StringEncoder())
    pipeline.addLast("handler", new SimpleChatClientHandler())
  }
}
