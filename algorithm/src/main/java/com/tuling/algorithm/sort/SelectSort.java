package com.tuling.algorithm.sort;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     选择排序
 *
 * 思想：从第一个元素开始遍历，找到列表中最小的元素，放在第一个位置。进行下一次遍历，从第二个元素开始，找到最小的元素放在第二个位置，
 * 以此类推。
 *
 * 选择排序 和 冒泡排序 非常相似。冒泡是比较之后就直接交换位置，选择排序是每次找最小的，找完之后再交换位置。
 *
 *
 * @date 2022/5/7 5:38 下午
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {23,45,17,11,13,89,72,26,3,17,11,13};

        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[index] > arr[j]){
                    index = j;
                }
            }
            if(i != index){
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }

        for (int a : arr){
            System.out.println(a);
        }
    }

}
