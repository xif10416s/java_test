package org.fxi.test.java.framework.io.netty.examples.simplechat

import java.io.{BufferedReader, InputStreamReader}

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

object SimpleChatClient {
  def main(args: Array[String]): Unit = {
    val group = new NioEventLoopGroup()
    try {
      val bootstrap = new Bootstrap().group(group).channel(classOf[NioSocketChannel]).handler(new SimpleChatClientInitializer)
      val channel = bootstrap.connect("localhost", 8087).sync.channel
      val in = new BufferedReader(new InputStreamReader(System.in))
      while ( {
        true
      }) channel.writeAndFlush(in.readLine + "\r\n")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally {
      group.shutdownGracefully()
    }
  }
}
