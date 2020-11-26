package org.fxi.test.java.alg.sort;

/**
 * @Author: fei
 * @Date: 2020/9/6 0006
 * L数组分成3段：
 * 已排序 ：L[1,2..,i-1]
 * 待排序 ：L[i]
 * 未排序 ： L[i+1,...n]
 * 1 初始L[1] 是一个已经排好序的子序列
 * 2. 对于L(i) [i>=2,i<=n]插入到排好序的子序列中
 *
 * a) 查找出L(i)插入位置k （ 1<=k<=i-1）
 * b) 将L[k,i-1] 向后移动一位
 * c) 将L(i)复制到L(k)
 */
public class 插入排序 {
  private static Integer[] array = new Integer[]{5,4,6,8,9,0,7,1,3,2};
  public static void main(String[] args) {
    sortDesc2();
  }


  public static void sortDesc2(){
    for(int i =1;i<=array.length-1;i++){ // 待排序的元素 [1,n-1]
      int data = array[i];
      for(int j = i;j>=0;j--) { // 已排序的元素,从i位置开始
        // 从小到大排序，如果i元素值小于，当前排序前一位元素值，需要交换当前元素和前一位元素
        if(j>=1 && data < array[j-1]){
          array[j] = array[j-1]; // 向后移动一个元素
        } else {
          // 如果j=0 或者 i元素数据大于 array[j-1],找到插入位置，插入，结束
          array[j] = data;
          break;
        }
      }
    }
    for (Integer integer : array) {
      System.out.print(integer);
    }
  }
}
