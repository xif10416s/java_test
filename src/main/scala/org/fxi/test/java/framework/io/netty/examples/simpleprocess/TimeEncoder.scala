package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class TimeEncoder extends MessageToByteEncoder[UnixTime] {
  override def encode(ctx: ChannelHandlerContext, msg: UnixTime, out: ByteBuf): Unit = {
    out.writeInt(msg.getValue().toInt)
  }
}
