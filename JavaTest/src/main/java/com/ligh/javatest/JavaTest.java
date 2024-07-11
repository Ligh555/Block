package com.ligh.javatest;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

public class JavaTest {

    public void test() {

        ArrayList<Integer> list = new ArrayList<>();

    }

    public void sort(int[] nums) {
        if (nums.length <= 1) return;
        sortInternal(nums, 0, nums.length - 1);
        for (Integer i : nums
        ) {
            System.out.print(i);
        }

        File file = new File("");
        file.lastModified();
        System.out.print("\n");
    }

    private void sortInternal(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        if (left + 1 == right) {
            if (nums[left] > nums[right]) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
            return;
        }
        int target = nums[left];

        int l = left + 1, r = right;
        while (l <= r) {
            while (l <= r && nums[l] < target) {
                l++;
            }
            while (l <= r && nums[r] > target) {
                r--;
            }
            if (l <= r) {
                int temp = nums[l];
                nums[l] = nums[r];
                nums[r] = temp;
            }
        }
        nums[left] = nums[r];
        nums[r] = target;
        sortInternal(nums, left, r - 1);
        sortInternal(nums, r + 1, right);
    }

    void test1(){
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(x ->x));
        Deque<Integer> deque = new ArrayDeque<>();

        
    }

}
