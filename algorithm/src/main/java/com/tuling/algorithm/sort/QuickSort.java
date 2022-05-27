package com.tuling.algorithm.sort;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     快速排序
 * @date 2022/5/7 4:18 下午
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {23,45,17,11,13,89,72,26,3,17,11,13};


    }


    public static void sort(int[] arr, int left, int right){
        // 首先，默认将数组第一个元素作为标识位
        int temp = arr[left];

        while (left < right){
            // 从数组的最左侧开始查找，直到找到一个比标识值大的值
            while (left < right && arr[left] <= temp){
                left++;
            }
            // 在左边找到更大的值，则将这个值放在标识值的位置
            arr[left] = arr[left];


        }

    }

}
