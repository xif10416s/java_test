package org.fxi.test.java.base.collections;

import java.util.ArrayList;
import org.junit.Test;

public class HashMapTester {

  public volatile  int a = 0;
  @Test
  public void test01() {
    a=3;
    new ArrayList<String>().stream().map(s->s);
  }
}
