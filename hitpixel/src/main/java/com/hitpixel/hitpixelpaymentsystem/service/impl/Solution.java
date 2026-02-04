package com.hitpixel.hitpixelpaymentsystem.service.impl;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int solution(int[] A) {

        int n = A.length;
        boolean[] present = new boolean[n + 1];

        // Mark positives that are <= n
        for (int x : A) {
            if (x > 0 && x <= n) {
                present[x] = true;
            }
        }

        // Find smallest positive that isn't present
        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                return i;
            }
        }

        // If 1..n are all present, answer is n+1
        return n + 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(new int[]{1, 3, 6, 4, 1, 2});
    }
}
