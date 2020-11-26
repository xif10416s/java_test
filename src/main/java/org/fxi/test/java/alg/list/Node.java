package org.fxi.test.java.alg.list;

/**
 * @Author: fei
 * @Date: 2020/8/30 0030
 */
class Node {
  public Node prev;
  public Node next;
  public int key;

  public Node(int key) {
    this.key = key;
    prev = null;
    next = null;
  }
}
