package org.fxi.test.java.alg.sort;

/**
 * @Author: fei
 * @Date: 2020/9/6 0006
 * 两两比较，
 */
public class 冒泡排序 {
  private static Integer[] array = new Integer[]{5,4,6,8,9,0,7,1,3,2};
  public static void main(String[] args) {
    sort();
    for (Integer integer : array) {
      System.out.print(integer);
    }
  }

  public static void sort(){
    for (int i = 0; i < array.length - 1; i++) { // n -1 次
      for(int j = 0 ; j < array.length - i -1; j++){
        if(array[j] > array[j+1] ){
          int tmp = array[j];
          array[j] = array[j+1];
          array[j+1] = tmp;
        }
      }
    }
  }
}
