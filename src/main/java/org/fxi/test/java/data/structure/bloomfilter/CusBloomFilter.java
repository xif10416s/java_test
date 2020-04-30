package org.fxi.test.java.data.structure.bloomfilter;

import java.util.BitSet;

public class CusBloomFilter {
  /**
   * 位数组的大小
   */
  private static final int DEFAULT_SIZE = 2 << 24;

  /**
   * 通过这个数组可以创建 6 个不同的哈希函数
   */
  private static final int[] SEEDS = new int[]{3, 13, 46, 71, 91, 134};

  /**
   * 位数组。数组中的元素只能是 0 或者 1
   */
  private BitSet bits = new BitSet(DEFAULT_SIZE);

  /**
   * 存放包含 hash 函数的类的数组
   */
  private SimpleHash[] func = new SimpleHash[SEEDS.length];


  /**
   * 初始化多个包含 hash 函数的类的数组，每个类中的 hash 函数都不一样
   */
  public CusBloomFilter() {
    // 初始化多个不同的 Hash 函数
    for (int i = 0; i < SEEDS.length; i++) {
      func[i] = new SimpleHash(DEFAULT_SIZE, SEEDS[i]);
    }
  }

  /**
   * 添加元素到位数组
   */
  public void add(Object value) {
    for (SimpleHash f : func) {
      bits.set(f.hash(value), true);
    }
  }

  /**
   * 判断指定元素是否存在于位数组
   */
  public boolean contains(Object value) {
    boolean ret = true;
    for (SimpleHash f : func) {
      ret = ret && bits.get(f.hash(value));
    }
    return ret;
  }

  /**
   * 静态内部类。用于 hash 操作！
   */
  public static class SimpleHash {

    private int cap;
    private int seed;

    public SimpleHash(int cap, int seed) {
      this.cap = cap;
      this.seed = seed;
    }

    /**
     * 计算 hash 值
     */
    public int hash(Object value) {
      int h;
      return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
    }

  }

  public static void main(String[] args){
//    CusBloomFilter filter = new CusBloomFilter();
//    String s1 ="aaabbbcccc";
//    System.out.println(filter.contains(s1));
//    filter.add(s1);
//    System.out.println(filter.contains(s1));
//
//    BitSet bits = new BitSet(8);
//    SimpleHash simpleHash = new SimpleHash(8,3);
//    int hash = simpleHash.hash(s1);
//    System.out.println(hash);
//    bits.set(hash,true);
//    System.out.println(bits.get(hash));

    BitSet bits2 = new BitSet(100);
    bits2.set(10);
    BitSet bits3 = new BitSet(100);
    bits3.set(10);
    System.out.println(bits2);
    System.out.println(bits2.cardinality());
    bits2.and(bits3);
    System.out.println(bits2);
    System.out.println(bits2.cardinality());
  }
}
