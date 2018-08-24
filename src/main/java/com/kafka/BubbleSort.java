package com.kafka;

import scala.actors.threadpool.Arrays;

/**
 * 类BubbleSort.java的实现描述：冒泡排序改进版 
 * @author rengq 2018年8月24日 上午9:35:50
 */
public class BubbleSort {
    public static void sort(int array[]) {
        int temp=0;
        
        //记录最后一次交换的位置
        int lastExchangeIndex = 0;
        //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder=array.length-1;
        
        for(int i=0;i<array.length;i++) {
            //有序标记，每一轮的初始是true
            boolean isSorted = true;
            for(int j=0;j<sortBorder;j++) {
                System.out.println(array[j]+":"+array[j+1]);
                if(array[j]>array[j+1]) {
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                    System.out.println("temp:"+temp);
                    //有元素交换，所以不是有序，标记变为false
                    isSorted=false;
                    //把无序数列的边界更新为最后一次交换元素的位置
                    lastExchangeIndex=j;
                }
            }
            System.out.println("第"+i+"次："+Arrays.toString(array));
            System.out.println("----------------------"+i);
            sortBorder=lastExchangeIndex;
            if(isSorted) {
                break;
            }
        }
    }
    public static void main(String[] args) {
        int[] s= {8,3,4,2,7,1,6,5};
        sort(s);
        System.out.println(Arrays.toString(s));
    }
}
