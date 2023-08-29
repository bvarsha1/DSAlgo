package DynamicProgramming;

import java.util.Arrays;

public class DynamicProgramming {

    /* DP - Remembering the past or Caching
     *  Optimization technique, given the conditions :
     *    1. Optimal substructure
     *    2. Overlapping sub-problems
     * 
     *      Top Down Approach : Recursion + Memoization / Caching
     *          Step 1 : Write base case
     *          Step 2 : Check if the state is already computed
     *          Step 3 : Otherwise compute the state using recursion and store it
     *      Bottom Up Approach : Iteration
     */

    // Time complexity : O(K^n)
    public int countWaysRecur(int n, int k) {
        // base case
        if(n == 0) {
            return 1;
        }
        if(n < 0) {
            return 0;
        }

        // recursive case
        int ans = 0;
        for(int jump = 1; jump <= k; jump++) {
            ans += countWaysRecur(n - jump, k);
        }

        return ans;
    }

    // Time complexity : O(nk)
    public int countWaysTD(int n, int k, int[] dp) {
        // base case
        if(n == 0) {
            return 1;
        }
        if(n < 0) {
            return 0;
        }

        // check if state is calculated
        if(dp[n] != 0)
            return dp[n];

        // recursive case
        int ans = 0;
        for(int jump = 1; jump <= k; jump++) {
            ans += countWaysTD(n - jump, k, dp);
        }

        // set the state
        dp[n] = ans;

        return ans;
    }

    // Time complexity : O(nk)
    public int countWaysBU(int n, int k) {
        int[] dp = new int[1000];
        dp[0] = dp[1] = 1;

        for(int i = 2; i <= n; i++) {
            for(int j = 1; j <= k && (i-j >= 0); j++) {
                dp[i] += dp[i - j];
            }
        }

        return dp[n];
    }

    // Time complexity : O(n)
    public int countWaysBUOpt(int n, int k) {
        int[] dp = new int[1000];
        dp[0] = dp[1] = 1;

        for(int i = 2; i <= k; i++) {
            dp[i]  = 2 * dp[i - 1];
        }
        for(int i = k+1; i <= n; i++) {
            dp[i]  = 2 * dp[i - 1] - dp[i - k - 1];
        }

        return dp[n];
    }

    public int minCoinChangeTD(int chg, int[] coins, int[] dp) {
        // base case
        if(chg == 0)
            return 0;

        if(dp[chg] != 0)
            return dp[chg];
        
        int result = Integer.MAX_VALUE;
        for(int coin : coins) {
            if( chg - coin >= 0 ) {
                int subProb = minCoinChangeTD(chg - coin, coins, dp);
                result = Math.min(result, subProb + 1);
            }
        }

        dp[chg] = result;
        return dp[chg];
    }

    public int minCoinChangeBU(int chg, int[] coins) {
        int[] dp  = new int[chg + 1];
        Arrays.fill(dp, 0);

        dp[0] = 0;
        for(int i = 1; i <= chg; i++) {
            // assign int_max to be able to find sub-probs without sols
            dp[i] = Integer.MAX_VALUE;

            for(int coin : coins) {
                if(i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[chg] == Integer.MAX_VALUE ? -1 : dp[chg];
    }

    public int maxProfitFromRodCuttingTD(int n, int[] price, int[] dp) {
        // base case
        if(n <= 0) return 0;

        if(dp[n] != 0) return dp[n];

        // recursive case
        int ans = 0;
        for(int i = 0; i < n; i++) {
            int cut = i + 1;
            int curr = price[i] + maxProfitFromRodCuttingTD(n - cut, price, dp);
            ans = Math.max(ans, curr);
        }
        dp[n] = ans;
        return ans;
    }

    public int maxProfitFromRodCuttingBU(int n, int[] price) {
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for(int len = 1; len <= n; len++) {
            int ans = Integer.MIN_VALUE;
            for(int i = 0; i < len; i++) {
                int cut = i + 1;
                int curr = price[i] + dp[len - cut];
                ans = Math.max(curr, ans);
            }
            dp[len] = ans;
        }

        return dp[n];
    }

    public int minArrayJumpsTD(int[] jumps, int n, int i, int[] dp) {
        //base case
        // 1. reached end of array - return 0
        if(i == n - 1) {
            return 0;
        }
        // 2. reached outside of array bounds
        if(i >= n) {
            return Integer.MAX_VALUE;
        }

        // check if state is calculated
        if(dp[i] != 0)
            return dp[i];

        int minSteps = Integer.MAX_VALUE;
        int maxJump = jumps[i];

        for(int jump = 1; jump <= maxJump; jump++) {
            int next = i + jump;
            int subProb = minArrayJumpsTD(jumps, n, next, dp);
            if(subProb != Integer.MAX_VALUE) {
                minSteps = Math.min(minSteps, subProb + 1);
            }
        }
        dp[i] = minSteps;
        return dp[i];
    }

    public int minCostFrogJumpTD(int[] h, int n, int i, int[] dp) {
        // base case
        if(i == n - 1)
            return 0;
        
        // check if state exists
        if(dp[i] != 0)
            return dp[i];

        // recursion
        int jump1 = (i + 1 < n) ? Math.abs((h[i + 1] - h[i])) + minCostFrogJumpTD(h, n, i + 1, dp) : Integer.MAX_VALUE;
        int jump2 = (i + 2 < n) ? Math.abs((h[i + 2] - h[i])) + minCostFrogJumpTD(h, n, i + 2, dp) : Integer.MAX_VALUE;
        int minCost = Math.min(jump1, jump2);

        dp[i] = minCost;
        return dp[i];
    }

    public int minCostFrogJumpBU(int[] heights) {
        int n = heights.length;
        int[] dp = new int[n];

        dp[0] = 0;
        dp[1] = Math.abs(heights[1] - heights[0]);
        for(int i = 2; i < n; i++) {
            int jump1 = Math.abs(heights[i] - heights[i-1]) + dp[i-1];
            int jump2 = Math.abs(heights[i] - heights[i-2]) + dp[i-2];
            dp[i] = Math.min(jump1, jump2);
        }

        return dp[n-1];
    }

    public int maxNonAdjSumTD(int[] arr, int n, int i, int dp[]) {
        // base case
        if(i >= n) return 0;

        // check if state exists
        if(dp[i] != 0)
            return dp[i];

        // recursion
        int inc = arr[i] + maxNonAdjSumTD(arr, n, i + 2, dp);
        int exc = maxNonAdjSumTD(arr, n, i + 1, dp);
        //assignment
        dp[i] = Math.max(inc, exc);

        return dp[i];
    }

    public int maxNonAdjSumBU(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        // edge case
        if(n == 1) {
            return Math.max(arr[0], 0);
        } else if(n == 2) {
            return Math.max(0, Math.max(arr[0], arr[1]));
        }

        dp[0] = Math.max(arr[0], 0);
        dp[1] = Math.max(0, Math.max(arr[0], arr[1]));

        // for every state
        for(int i = 2; i < n; i++) {
            int inc = dp[i - 2] + arr[i];
            int exc = dp[i - 1];
            dp[i] = Math.max(inc, exc);
        }

        return dp[n-1];
    }

    public int lisTD(int[] arr) {
        int[] dp = new int[arr.length];
        Arrays.fill(dp, 0);

        int maxLIS = 1;
        for(int i = 0; i < arr.length; i++) {
            maxLIS = Math.max(maxLIS, lisTDHelper(arr, arr.length, i, dp));
        }

        return maxLIS;
    }

    public int lisTDHelper(int[] arr, int n, int i, int[] dp) {
        // base case
        if(i >= n)
            return 0;
        
        // check if state is computed
        if(dp[i] != 0)
            return dp[i];
        
        // recursion
        int maxLen = 1;
        for(int j = i + 1; j < n; j++) {
            if(arr[j] > arr[i]) {
                int subProb = 1 + lisTDHelper(arr, n, j, dp);
                maxLen = Math.max(maxLen, subProb);
            }
        }

        dp[i] = maxLen;
        return maxLen;
    }

    public int lisBU(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int len = 1;
        for(int i = 1; i < n; i++) {
            // for all possibilities
            for(int j = 0; j < i; j++) {
                if(arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    // as the longest subsequence could end anywhere, not necessarily the last element
                    len = Math.max(len, dp[i]);
                }
            }
        }
        return len;
    }

    public int boxStackMaxBU(int[][] boxes) {
        Arrays.sort(boxes, (a, b) -> Integer.compare(a[2], b[2]));
        int n = boxes.length;
        int[] dp = new int[n];
        
        for(int i = 0; i < n; i++) {
            dp[i] = boxes[i][2];
        }

        int maxH = Integer.MIN_VALUE;
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(boxes[j][0] < boxes[i][0] && boxes[j][1] < boxes[i][1] && boxes[j][2] < boxes[i][2]) {
                    dp[i] = Math.max(boxes[i][2] + dp[j], dp[i]);
                    maxH = Math.max(maxH, dp[i]);
                }
            }
        }

        return maxH;
    }

    public int countBSTsTD(int n, int[] dp) {
        // base case
        if(n == 0 || n == 1)
            return 1;

        // check if state is computed
        if(dp[n] != 0)
            return dp[n];

        // recursion
        int ans = 0;
        for(int i = 1; i <= n; i++) {
            int x = countBSTsTD(i - 1, dp);
            int y = countBSTsTD(n - i, dp);
            // we do summation on all the recursive calls
            ans += x*y;
        }

        dp[n] = ans;
        return dp[n];
    }

    public int countBSTsBU(int n) {
        int[] dp = new int[n + 1];

        dp[0] = dp[1] = 1;

        // for every state in cache
        for(int i = 2; i <= n; i++) {
            // must do summation on all values of j
            // such that 1 <= j <= i
            for(int j = 1; j <= i; j++) {
                // 2 possibilies
                int x = dp[j - 1];
                int y = dp[i - j];
                dp[i] += x*y;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();

        // Problem 1 : N-K Ladders, count number of ways to reach N, given at most k jumps could be taken
        int[] dpArr = new int[1000];
        System.out.println("The number of ways to climb n ladders is (recursive) : " + dp.countWaysRecur(4, 3));
        System.out.println("The number of ways to climb n ladders is (top down dp) : " + dp.countWaysTD(4, 3, dpArr));
        System.out.println("The number of ways to climb n ladders is (bottom up dp) : " + dp.countWaysBU(4, 3));
        System.out.println("The number of ways to climb n ladders is (bottom up optimized dp) : " + dp.countWaysBUOpt(6, 4));

        // Problem 2 : Coin change
        int[] coins = new int[] {1, 3, 7, 10};
        int change = 15;
        int[] dpArr2 = new int[change + 1];
        Arrays.fill(dpArr2, 0);
        System.out.println("The minimum number of coing to make change (top down dp)  : " + dp.minCoinChangeTD(change, coins, dpArr2));
        System.out.println("The minimum number of coing to make change (bottom up dp) : " + dp.minCoinChangeBU(change, coins));

        // Problem 3 : Rod cutting for max profit
        int length = 8;
        int[] price = new int[] {1, 5, 8, 9,10, 17, 17, 20};
        int[] dpArr3 = new int[length + 1];
        Arrays.fill(dpArr3, 0);
        System.out.println("The max profit gained from cutting rod and selling (top down dp)  : " + dp.maxProfitFromRodCuttingTD(length, price, dpArr3));
        System.out.println("The max profit gained from cutting rod and selling (bottom up dp) : " + dp.maxProfitFromRodCuttingBU(length, price));

        // Problem 4 : Array jumps - minimum
        int[] jumps = new int[] {3, 4, 2, 1, 2, 3, 7, 1, 1, 3};
        int n = jumps.length;
        int[] dpArr4 = new int[n];
        Arrays.fill(dpArr4, 0);
        System.out.println("The min jumps to reach end of array : " + dp.minArrayJumpsTD(jumps, n, 0, dpArr4));

        // Problem 5 : Frog's minimum jump
        int[] heights = new int[] {10, 30, 40 ,20};
        int[] dpArr5 = new int[heights.length];
        Arrays.fill(dpArr5, 0);
        System.out.println("The min cost jump the frog can make (top down dp)  : " + dp.minCostFrogJumpTD(heights, heights.length, 0, dpArr5));
        System.out.println("The min cost jump the frog can make (bottom up dp) : " + dp.minCostFrogJumpBU(heights));

        // Problem 6 : Max non-adjacent sum
        int[] arr = new int[] {6, 10, 12, 7, 9, 14};
        int[] dpArr6 = new int[arr.length];
        Arrays.fill(dpArr6, 0);
        System.out.println("Max non-adjacent sum from array (top down dp)  : " + dp.maxNonAdjSumTD(arr, arr.length, 0, dpArr6));
        System.out.println("Max non-adjacent sum from array (bottom up dp) : " + dp.maxNonAdjSumBU(arr));

        // Problem 7 : Longest increasing subsequence
        int[] arr2 = {50, 4, 10, 8, 30, 100};
        System.out.println("Longest increasing subsequence size (top down dp)  : " + dp.lisTD(arr2));
        System.out.println("Longest increasing subsequence size (bottom up dp) : " + dp.lisBU(arr2));

        // Problem 8 : Box stacking
        int[][] boxes = {
            {2, 1, 2},
            {3, 2, 3},
            {2, 2, 8},
            {2, 3, 4},
            {2, 2, 1},
            {4, 4, 5}
        };
        System.out.println("Max height of stacked boxes (bottom up dp) : " + dp.boxStackMaxBU(boxes));

        // Problem 9 : Counting trees
        int nodeCnt = 3;
        int[] dpArr7 = new int[nodeCnt + 1];
        Arrays.fill(dpArr7, 0);
        System.out.println("The number of BSTs that can be formed are (top down dp) : " + dp.countBSTsTD(nodeCnt, dpArr7));
        System.out.println("The number of BSTs that can be formed are (bottom up dp) : " + dp.countBSTsBU(nodeCnt));
    }
}
