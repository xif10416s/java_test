package org.fxi.test.java.alg.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fei
 * @Date: 2020/9/13 0013
 */
public class Graph {
  // 顶点个数
  public int vexsNum ;
  // 边矩阵，邻接矩阵
  public int[][] edges;

  public Graph(int vexsNum , int[][] edges){
    this.vexsNum = vexsNum;
    this.edges = edges;
  }

  /**
   * 第i个元素的邻居节点
   * @param i
   * @return
   */
  public List<Integer> getNeighbors(int i){
    ArrayList<Integer> neihbors = new ArrayList<>();
    for (int j = 0; j < edges[i].length; j++) {
      if(i != j){
        if(edges[i][j] != 0){
          neihbors.add(j);
        }
      }
    }
    return neihbors;
  }

  public static Graph getTemplateGraph(){
    int[][] ints = {
        {0,1,1,0,0,0,0,0},
        {1,0,0,1,1,0,0,0},
        {1,0,0,0,0,1,1,0},
        {0,1,0,0,1,0,0,0},
        {0,1,0,1,0,0,0,1},
        {0,0,1,0,0,0,0,0},
        {0,0,1,0,0,0,0,0},
        {0,0,0,0,1,0,0,0},
    };

    Graph graph = new Graph(8, ints);
    return graph;
  }

  public static Graph getTemplateGraphWeight(){
    int[][] ints = {
        {0,6,1,5,Integer.MAX_VALUE,Integer.MAX_VALUE},
        {6,0,5,Integer.MAX_VALUE,3,Integer.MAX_VALUE},
        {1,5,0,5,6,4},
        {5,Integer.MAX_VALUE,5,0,Integer.MAX_VALUE,2},
        {Integer.MAX_VALUE,3,6,Integer.MAX_VALUE,0,6},
        {Integer.MAX_VALUE,Integer.MAX_VALUE,4,2,6,0}
    };

    Graph graph = new Graph(ints[0].length, ints);
    return graph;
  }

  /**
   *                          0
   *               1                    2
   *         3     --    4         5              6
   *                         7
   *
   * @param args
   */
  public static void main(String[] args) {

    List<Integer> neighbors = getTemplateGraph().getNeighbors(0);
    for (Integer neighbor : neighbors) {
      System.out.println(neighbor);
    }
  }
}
