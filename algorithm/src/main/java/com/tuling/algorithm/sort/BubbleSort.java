package com.tuling.algorithm.sort;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     冒泡排序
 *
 * 核心思想，利用双循环，将第一个值与后面的每一个值相比较，只要小于，则交互两个值的位置。这样就能保证第一个值一定是小于后面所有值的，
 * 然后进行第二次循环，直到整个数组排好序。
 *
 * 优点：比较好理解
 * 缺点：时间复杂度高，需要用双循环。
 * 时间复杂度：o(n^2)
 * 空间复杂度：o(1)
 *
 * @date 2022/5/7 4:19 下午
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {23,45,17,11,13,89,72,26,3,17,11,13};

        // 从小到大排序
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        for (int a : arr){
            System.out.println(a);
        }

    }


}
