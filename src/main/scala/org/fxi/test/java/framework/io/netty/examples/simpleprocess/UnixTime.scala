package org.fxi.test.java.framework.io.netty.examples.simpleprocess

import java.util.Date

class UnixTime(value:Long) {
  def this (){
    this(System.currentTimeMillis() / 1000L + 2208988800L)
  }
  def getValue(): Long = {
    return this.value
  }

  override def toString: String = new Date((getValue() - 2208988800L) * 1000L).toString
}
