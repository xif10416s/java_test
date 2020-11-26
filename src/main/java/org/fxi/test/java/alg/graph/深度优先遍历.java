package org.fxi.test.java.alg.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: fei
 * @Date: 2020/9/13 0013 递归或栈 树的先序遍历
 */
public class 深度优先遍历 {

  static Graph templateGraph = Graph.getTemplateGraph();
  static int vemNum = templateGraph.vexsNum;
  static boolean[] visited = new boolean[vemNum];

  public static void main(String[] args) {
    // 初始化
    // 1 标记数组
    dfs(0);
  }

  /**
   *                          0
   *               1                    2
   *         3     --    4         5              6
   *                         7
   *
   * @param args
   */
  public static void dfs(int i) {
    if(visited[i]){
      return ;
    }
    // 访问i
    System.out.println(i);
    visited[i] = true;
    List<Integer> neighbors = templateGraph.getNeighbors(i);
    for (Integer neighbor : neighbors) {
      dfs(neighbor);
    }


  }
}
