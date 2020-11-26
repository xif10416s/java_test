package org.fxi.test.java.alg.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: fei
 * @Date: 2020/9/13 0013 队列
 */
public class 广度优先遍历 {

  static Graph templateGraph = Graph.getTemplateGraph();
  static int vemNum = templateGraph.vexsNum;
  static boolean[] visited = new boolean[vemNum];

  public static void main(String[] args) {
    // 初始化
    // 1 标记数组
    LinkedList<Integer> queue = new LinkedList<Integer>();
    for (int i = 0; i < vemNum; i++) {
      if (!visited[i]) {
        bfs(i, queue);
      }
    }
  }

  /**
   * 广度优先
   */
  public static void bfs(int i, LinkedList<Integer> queue) {
    queue.add(i);
    while (!queue.isEmpty()) {
      //先弹出访问
      Integer pop = queue.pop();
      if (!visited[pop]) {
        System.out.println(pop);
        // 标记已访问
        visited[pop] = true;
        // 获取邻居节点，入队
        List<Integer> neighbors = templateGraph.getNeighbors(pop);
        for (Integer neighbor : neighbors) {
          // 未访问的添加
          queue.add(neighbor);
        }
      }
    }

  }
}
