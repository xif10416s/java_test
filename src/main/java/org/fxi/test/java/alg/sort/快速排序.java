package org.fxi.test.java.alg.sort;

/**
 * @Author: fei
 * @Date: 2020/9/6 0006
 * 给基准数据找其正确的索引位置的过程
 * 		a. 基本概念：
 * 			i. 就平均时间而言，快速排序是目前认为最好的一种内部排序方法
 * 			ii. 分治法思想的体现
 * 			iii. 轴值（pivot):用于将记录集分割为两个部分的那个键值
 * 			iv. 分割（partition): 将记录集分为两个部分，前面部分记录的键值都要比轴值小，后面部分的键值都要比轴值大
 * 		b. 基本思想：
 * 			i. 对冒泡法的之中改进，选取一个轴值，然后根据此轴值通过一趟排序对记录集进行一次分割，
 * 			然后对分割后产生的两个记录子集分别快速排序
 * parttion算法
 * 初始化标记low为划分部分的第一个元素，high为最后一个元素的位置，然后不断的移动两个标记并交换元素
 * 1）high向前移动找到第一个比pivot小的元素
 * 2）low向后移动找到第一个比pivot大的元素
 * 3）交换当前两个元素位置
 * 4）执行1,2,3 到low大于等于high
 */


public class 快速排序 {
  private static Integer[] array = new Integer[]{5,4,6,8,9,0,7,1,3,2};
  public static void main(String[] args) {
    quickSort(array,0,array.length-1);
    for (Integer integer : array) {
      System.out.print(integer);
    }
  }
  public static void quickSort(Integer[] array  , int low ,int high){
      if(low < high){
        //分段位置下标
        int partition = getPartition(array, low, high);
        //递归调用排序
        //左边排序
        quickSort(array, low, partition - 1);
        //右边排序
        quickSort(array, partition + 1, high);
      }
  }

  /**
   *  low->             <-high
   *  5,4,6,8,9,0,7,1,3,2vg c
   * @param arr
   * @param low
   * @param high
   * @return
   */
  public static int getPartition(Integer[] arr,int low ,int high){
    int pivot = arr[low];
    while(low < high){
      while(low<high && arr[high] > pivot){
        high --;
      }
      arr[low]=arr[high];
      while(low<high && arr[low] < pivot){
        low ++;
      }
      arr[high]=arr[low];
    }
    arr[low] = pivot;
    return low;
  }
}
