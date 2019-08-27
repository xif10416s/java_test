package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import java.util

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class TimeDecoder extends ByteToMessageDecoder {
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
    if (in.readableBytes < 4)
      return
    out.add(new UnixTime(in.readUnsignedInt()))
  }
}
