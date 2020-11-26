package org.fxi.test.java.alg.sort;

/**
 * @Author: fei
 * @Date: 2020/9/6 0006
 * 	a. 把序列按一定间隔分组，对每组使用直接插入排序
 *	b. 随着间隔减小，一直到1，使得整个序列有序
 */
public class 希尔排序 {
  private static Integer[] array = new Integer[]{5,4,6,8,9,0,7,1,3,2};
  public static void main(String[] args) {
    for(int gap= array.length/2;gap>=1;gap=gap/2){
      //从第gap个元素，逐个对其所在组进行直接插入排序操作
      for(int i=gap;i<array.length;i++){
        System.out.println(gap +":"+i );
        int j = i;
        while(j-gap>=0 && array[j]<array[j-gap]){
          //插入排序采用交换法
          int tmp ;
          tmp = array[j];
          array[j]= array[j-gap];
          array[j-gap] = tmp;
          j-=gap;
        }
      }
    }
    for (Integer integer : array) {
      System.out.print(integer);
    }
  }
}
