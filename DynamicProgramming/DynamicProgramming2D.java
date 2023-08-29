package DynamicProgramming;

import java.util.Arrays;

public class DynamicProgramming2D {

    public int[][] createDPArray(int r, int c, int fill) {
        int[][] dp = new int[r][c];
        for(int[] row : dp) {
            Arrays.fill(row, fill);
        }
        return dp;
    }

    public void printDPArray(int[][] dp) {
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int lcsRecur(String s1, String s2, int i, int j) {
        // base case
        if(i == s1.length() || j == s2.length()) {
            return 0;
        }

        // recursive case
        if(s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsRecur(s1, s2, i + 1, j + 1);
        }
        int op1 = lcsRecur(s1, s2, i + 1, j);
        int op2 = lcsRecur(s1, s2, i, j + 1);

        return Math.max(op1, op2);
    }

    public int lcsTD(String s1, String s2, int i, int j, int[][] dp) {
        // base case
        if(i == s1.length() || j == s2.length()) {
            return 0;
        }

        // check if state is calculated
        if(dp[i][j] != -1)
            return dp[i][j];

        // recursive case
        if(s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsTD(s1, s2, i + 1, j + 1, dp);
        }
        int op1 = lcsTD(s1, s2, i + 1, j, dp);
        int op2 = lcsTD(s1, s2, i, j + 1, dp);

        dp[i][j] = Math.max(op1, op2);
        return dp[i][j];
    }

    public int lcsBU(String s1, String s2) {
        int[][] dp = createDPArray(s1.length() + 1, s2.length() + 1, 0);

        for(int i = 1; i <= s1.length(); i++) {
            for(int j = 1; j <= s2.length(); j++) {
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); 
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public int maxProfitFromWinesTD(int[] price, int l, int r, int y, int[][] dp) {
        // base case
        if(l > r)
            return 0;

        // check if state exists
        if(dp[l][r] != 0)
            return dp[l][r];

        // recursion
        int left = (y * price[l]) + maxProfitFromWinesTD(price, l + 1, r, y + 1, dp);
        int right = (y * price[r]) + maxProfitFromWinesTD(price, l, r - 1, y + 1, dp);
        dp[l][r] = Math.max(left, right);

        return dp[l][r];
    }

    public int maxProfitFromWinesBU(int[] price) {
        int n = price.length;
        int[][] dp = createDPArray(n, n, 0);

        for(int i = n - 1; i >= 0; i--) {
            for(int j = 0; j < n; j++) {
                if(i == j) {
                    dp[i][j] = price[i]*n;
                } else if(i < j) {
                    int y = n - (j - i);
                    int left = price[i]*y + dp[i + 1][j];
                    int right = price[j]*y + dp[i][j - 1];
                    dp[i][j] = Math.max(left, right);
                }
            }
        }

        return dp[0][n - 1];
    }

    public int subsequenceCountTD(String s1, String s2, int i, int j, int[][] dp) {
        // base case
        if( ( i == -1 && j == -1 ) || j == -1)
            return 1;
        if(i == -1) {
            return 0;
        }

        // check if state is computed
        if(dp[i][j] != 0)
            return dp[i][j];

        // recursion
        dp[i][j] = subsequenceCountTD(s1, s2, i - 1, j, dp);
        if(s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] += subsequenceCountTD(s1, s2, i - 1, j - 1, dp);
        }
        return dp[i][j];
    }

    public int subsequenceCountBU(String s1, String s2) {
        int[][] dp = createDPArray(s1.length() + 1, s2.length() + 1, 0);
        // the above base case can be assessed to give 1 below
        for(int i = 0; i <= s1.length(); i++) {
            dp[i][0] = 1;
        }

        for(int i = 1; i <= s1.length(); i++) {
            for(int j = 1; j <= s2.length(); j++) {
                dp[i][j] = dp[i - 1][j];
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public int knapsackTD(int[] wts, int[] prices, int N, int W, int[][] dp) {
        // base case
        if(N == 0 || W == 0) {
            // cannot put more items in the bag
            return 0;
        }

        // check if the state is computed
        if(dp[N][W] != 0)
            return dp[N][W];

        // recursion
        int inc = 0, exc = 0;
        if(W - wts[N - 1] >= 0) {
            inc = prices[N - 1] + knapsackTD(wts, prices, N - 1, W - wts[N - 1], dp);
        }
        exc = knapsackTD(wts, prices, N - 1, W, dp);

        dp[N][W] = Math.max(inc, exc);

        return dp[N][W];
    }

    public int knapsackBU(int[] wts, int[] prices, int W) {
        int N = wts.length;
        int[][] dp = createDPArray(N + 1, W + 1, 0);

        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= W; j++) {
                int inc = 0, exc = 0;
                if(j - wts[i - 1] >= 0) {
                    inc = prices[i - 1] + dp[i - 1][j - wts[i - 1]];
                }
                exc = dp[i - 1][j];
                dp[i][j] = Math.max(inc, exc);
            }
        }

        return dp[N][W];
    }

    public static void main(String[] args) {
        DynamicProgramming2D dp2d = new DynamicProgramming2D();
        int[][] dp;

        // Problem 1 : Least Common Subsequence
        String s1 = "ABCD";
        String s2 = "ABEDG";
        // Recursion
        System.out.println("The length of Least Common Subsequence (recursion) : " + dp2d.lcsRecur(s1, s2, 0, 0));
        // Top-down approach
        dp = dp2d.createDPArray(s1.length(), s2.length(), -1);
        System.out.println("The length of Least Common Subsequence (top down) : " + dp2d.lcsTD(s1, s2, 0, 0, dp));
        // Bottom-up approach
        System.out.println("The length of Least Common Subsequence (bottom up) : " + dp2d.lcsBU(s1, s2));

        // Problem 2 : Selling wines
        int[] price = {2, 3, 5 ,1, 4};
        // Top-down approach
        dp = dp2d.createDPArray(price.length, price.length, 0);
        System.out.println("The max profit that could be generated (top down) : " + dp2d.maxProfitFromWinesTD(price, 0, price.length - 1, 1, dp));
        // Bottom-up approach
        System.out.println("The max profit that could be generated (bottom up) : " + dp2d.maxProfitFromWinesBU(price));

        // Problem 3 : Subsequence count
        String s = "ABCDCE";
        String r = "ABC";
        dp = dp2d.createDPArray(s.length(), r.length(), 0);
        System.out.println("Number of times string " + r + " occurs as subsequence in " + s + " : " + dp2d.subsequenceCountTD(s, r, s.length() - 1, r.length() - 1, dp));
        System.out.println("Number of times string " + r + " occurs as subsequence in " + s + " : " + dp2d.subsequenceCountBU(s, r));

        // Problem 4 : Knapsack
        int[] wts = new int[] {2, 7 , 3, 4};
        int[] prices = {5, 20 , 20, 10};
        int N = wts.length;
        int W = 11;
        dp = dp2d.createDPArray(N + 1, W + 1, 0);
        System.out.println("The maximum value that could be put in the bag is (top down dp) : " + dp2d.knapsackTD(wts, prices, N, W, dp));
        // dp2d.printDPArray(dp);
        System.out.println("The maximum value that could be put in the bag is (bottom up dp) : " + dp2d.knapsackBU(wts, prices, W));
    }
}