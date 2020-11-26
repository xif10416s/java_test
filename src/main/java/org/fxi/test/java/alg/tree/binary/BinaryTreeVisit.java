package org.fxi.test.java.alg.tree.binary;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: fei
 * @Date: 2020/9/5 0005
 *
 *                  1
 *              2       3
 *           4    5   6   7
 *        8    9
 *
 */
public class BinaryTreeVisit {
  class BiTree {
    private int date;
    private BiTree left;
    private BiTree right;

    public BiTree(int date , BiTree left,BiTree right){
      this.date = date;
      this.left = left;
      this.right = right;
    }
  }

   public  BiTree  getRoot() {
    BiTree biTree8 = new BiTree(8, null, null);
    BiTree biTree9 = new BiTree(9, null, null);
    BiTree biTree4 = new BiTree(4, biTree8, biTree9);
    BiTree biTree5 = new BiTree(5, null, null);
    BiTree biTree2 = new BiTree(2, biTree4, biTree5);
    BiTree biTree6 = new BiTree(6, null, null);
    BiTree biTree7 = new BiTree(7, null, null);
    BiTree biTree3 = new BiTree(3, biTree6, biTree7);
    return new BiTree(1, biTree2, biTree3);
  }

  public static void main(String[] args) {
    BinaryTreeVisit binaryTreeVisit = new BinaryTreeVisit();
    BiTree root = binaryTreeVisit.getRoot();
    递归中序(root);
    System.out.println("\n");
    堆栈中序(root);
    System.out.println("\n");
    递归先序(root);
    System.out.println("\n");
    堆栈先序(root);
    System.out.println("\n");
    层次遍历(root);
  }

  public static void 递归先序(BiTree tree){
    if(tree != null){
      System.out.print(tree.date);
      递归先序(tree.left);
      递归先序(tree.right);
    }
  }

  public static void 堆栈先序(BiTree root){
    Stack<BiTree> stack  = new Stack<>();
    BiTree bi = root;
    if(root == null){
      return;
    }
    while(bi != null || !stack.empty()){
      if(bi != null){
        System.out.print(bi.date); //  先打印节点
        stack.push(bi);
        bi = bi.left;
      } else {
        BiTree pop = stack.pop();  // 没有左边节点，弹出处理，放入右边节点
        if(pop != null) {
          bi = pop.right;
        }
      }
    }
  }

  public static void 递归中序(BiTree tree){
    if(tree != null){
      递归中序(tree.left);
      System.out.print(tree.date);
      递归中序(tree.right);
    }
  }

  /**
   *  1. 扫描根节点的所有左侧节点
   *  2. 出站一个节点，pop 访问（print)
   *  3. 扫描该节点的有节点，进堆栈
   *  4. 依次扫描该节点所有左侧节点，重复1
   * @param root
   */
  public static void 堆栈中序(BiTree root){
    Stack<BiTree> stack  = new Stack<>();
    BiTree bi = root;
    if(root == null){
      return;
    }
    while(bi != null || !stack.empty()){
      if(bi != null){    //  先收集左边节点
        stack.push(bi);
        bi = bi.left;
      } else {
        BiTree pop = stack.pop();  // 没有左边节点，弹出处理，放入右边节点
        if(pop != null) {
          System.out.print(pop.date);
          bi = pop.right;
        }
      }
    }
  }

  public static void 递归后序(BiTree tree){
    if(tree != null){
      递归先序(tree.left);
      递归先序(tree.right);
      System.out.println(tree.date);
    }
  }

  public static void 层次遍历(BiTree root){
    LinkedList<BiTree> queue = new LinkedList<BiTree>();
    if( root==null ){
      return;
    }
    BiTree bi = root;
    queue.add(bi);
    while(!queue.isEmpty()){
      BiTree pop = queue.pop();
      if(pop != null){
        System.out.print(pop.date);
        queue.add(pop.left);
        queue.add(pop.right);
      }
    }
  }
}
