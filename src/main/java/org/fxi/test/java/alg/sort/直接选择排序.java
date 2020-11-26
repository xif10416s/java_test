package org.fxi.test.java.alg.sort;

/**
 * @Author: fei
 * @Date: 2020/9/12 0012
 */
public class 直接选择排序 {
  private static Integer[] array = new Integer[]{5,4,6,8,9,0,7,1,3,2};
  public static void main(String[] args) {
    for (int i = 0; i < array.length - 1; i++) {
      for(int j = i + 1 ; j < array.length;j++){
        if(array[i] > array[j]){
          int tmp = array[i];
          array[i] = array[j];
          array[j] = tmp;
        }
      }
    }
    for (Integer integer : array) {
      System.out.print(integer);
    }
  }

}
