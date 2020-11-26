package org.fxi.test.java.alg.list;

/**
 * @Author: fei
 * @Date: 2020/8/30 0030
 * 循环双链表
 */
class LinkedList {
  private Node sentinel;

  public LinkedList() {
    sentinel = new Node(Integer.MIN_VALUE);
    sentinel.next = sentinel;
    sentinel.prev = sentinel;
  }

  public void insert(Node x) {
    x.next = sentinel.next;
    sentinel.next.prev = x;
    sentinel.next = x;
    x.prev = sentinel;
  }

  public void insert(int key) {
    Node i = new Node(key);
    insert(i);
  }

  public void delete(Node x) {
    x.prev.next = x.next;
    x.next.prev = x.prev;
  }

  public void delete(int key) {
    delete(search(key));
  }

  // search外部接口
  public Node search(int key) {
    return search(sentinel.next, key);
  }

  // search实体
  private Node search(Node i, int key) {
    if (i.key == key){
      return i;
    }

    else if (i == sentinel){
      return i;
    }
    i = i.next;
    return search(i, key);
  }

  public void print() {
    Node i = sentinel.next;
    while (i != sentinel) {
      System.out.print(i.key + " ");
      i = i.next;
    }
    System.out.println();
  }

  public static void main(String[] args){
    LinkedList linkedList = new LinkedList();

    linkedList.insert(2);
    linkedList.insert(1232);
    Node search = linkedList.search(2);
    System.out.println(search.key);
    search = linkedList.search(1232);
    System.out.println(search.key);
    linkedList.print();
  }
}