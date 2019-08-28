package org.fxi.test.java.framework.io.netty.examples.simplechat

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.{DelimiterBasedFrameDecoder, Delimiters}
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}

class SimpleChatServerInitializer extends ChannelInitializer[SocketChannel] {
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline = ch.pipeline
    //最大长度8192,按行分隔
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter:_*))
    pipeline.addLast("decoder", new StringDecoder)
    pipeline.addLast("encoder", new StringEncoder)
    pipeline.addLast("handler", new SimpleChatServerHandler)

    System.out.println("SimpleChatClient:" + ch.remoteAddress + "连接上")
  }
}
