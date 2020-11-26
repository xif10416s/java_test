package org.fxi.test.java.alg.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: fei
 * @Date: 2020/9/13 0013 最小生成树 另图为G 一，任取一点v，加入树T 二，从U=G-T中取与T中顶点集合最短顶点加入T 三，循环直到T包含所有顶点
 */
public class Prim {

  static Graph g = Graph.getTemplateGraphWeight();
  static int vexNum = g.vexsNum;

  public static void main(String[] args) {

    // 初始化两个集合，一个是空的用于存放生成树的顶点的集合
    ArrayList<Integer> minTree = new ArrayList<>();
    // 一个是原图的顶点集合
    List<Integer> vexs = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5});
    HashSet<Integer> vexsSet = new HashSet<>(vexs);
    // 添加第一个节点
    minTree.add(0);
    vexsSet.remove(0);
    // T中顶点 与 vex - T中顶点选一个最小的加入T
    while (minTree.size() < vexNum) {
      int min = Integer.MAX_VALUE;
      int minV = -1;
      // T 所有 与 U所有 的最小
      for (int i = 0; i < minTree.size(); i++) {
        int u = minTree.get(i);
        for (int v : vexsSet) {
          if (u != v) {
            if (g.edges[u][v] < min) {
              min = g.edges[u][v];
              minV = v;
            }
          }
        }
      }
      minTree.add(minV);
      vexsSet.remove(minV);
    }
    for (Integer integer : minTree) {
      System.out.println(integer);
    }
  }
}
