package Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

import Arrays.ArrayProbs.Pair;

public class ArrayProbs {

    public int[] pairWithSum(int[] arr, int targetSum) {
        HashSet<Integer> seen = new HashSet<>();

        for(int x : arr) {
            int lookup = targetSum - x;
            if(seen.contains(lookup))
                return new int[] {lookup, x};
            seen.add(x);
        }
        
        return new int[] {-1, -1};
    }

    public ArrayList<int[]> tripletsWithSum(int[] arr, int targetSum) {
        Arrays.sort(arr);
        ArrayList<int[]> result = new ArrayList<>();

        int n = arr.length;
        for(int i = 0; i <= n - 3; i++) {
            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int currSum = arr[i];
                currSum += arr[j];
                currSum += arr[k];

                if(currSum == targetSum) {
                    result.add(new int[] {arr[i], arr[j], arr[k]});
                    j++;
                    k--;
                } else if(currSum > targetSum) {
                    k--;
                } else {
                    j++;
                }
            }
        }

        return result;
    }

    public int mountainLen(int[] arr) {
        int n = arr.length;

        int length = 0;
        for(int i = 1; i <= n - 2;) {
            // check arr[i] is a peak
            if(arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                int count = 1;

                // count left
                int j = i;
                while(j >= 1 && arr[j] > arr[j - 1]) {
                    j--;
                    count++;
                }

                // count right
                while (i <= n - 2 && arr[i] >= arr[i + 1]) {
                    i++;
                    count++;
                }

                length = Math.max(length, count);
            } else {
                i++;
            }
        }

        return length;
    }

    public int longestBand(int[] arr) {
        HashSet<Integer> exists = new HashSet<>();

        for(int x : arr) {
            exists.add(x);
        }

        int maxLen = 0;
        for(int i = 0; i < arr.length; i++) {
            // check if element can start a band
            if(exists.contains(arr[i] - 1))
                continue;
            
            int len = 1;
            int e = arr[i];
            while(exists.contains(++e))
                len++;

            maxLen = Math.max(maxLen, len);
        }

        return maxLen;
    }
    
    public int rainTrapped(int[] arr) {
        int n = arr.length;
        if(n <= 2) return 0;

        int[] left = new int[n];
        int[] right = new int[n];

        left[0] = arr[0];
        right[n - 1] = arr[n - 1];
        for(int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1], arr[i]);
            right[n - i - 1] = Math.max(right[n - i], arr[n - i - 1]);
        }

        int rainTrapped = 0;
        for(int i = 0; i < n; i++) {
            rainTrapped += Math.min(left[i], right[i]) - arr[i];
        }

        return rainTrapped;
    }

    public boolean outOfOrder(int[] arr, int i) {
        if(i == 0)
            return arr[i] > arr[i + 1];
        if(i == arr.length - 1)
            return arr[i] < arr[i - 1];

        return arr[i] > arr[i + 1] || arr[i] < arr[i - 1];
    }

    public int[] subarraySort(int[] arr) {
        int smallest = Integer.MAX_VALUE;
        int largest = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++) {
            // check if out of order
            if(outOfOrder(arr, i)) {
                smallest = Math.min(smallest, arr[i]);
                largest = Math.max(largest, arr[i]);
            }
        }
        if(smallest == Integer.MAX_VALUE)
            return new int[] {-1, -1};

        // find index to place smallest and largest
        int left = 0;
        while(smallest > arr[left]) left++;

        int right = arr.length - 1;
        while (largest < arr[right]) right--;

        return new int[] {left, right};
    }

    public int minSwaps(int[] arr) {
        Pair[] elementIndexMap = new Pair[arr.length];

        for(int i = 0; i < arr.length; i++) {
            elementIndexMap[i] = new Pair(arr[i], i);
        }
        Arrays.sort(elementIndexMap, (Pair a, Pair b) -> Integer.compare(a.getElement(), b.getElement()));

        for(Pair p : elementIndexMap) {
            System.out.println(p.getElement() + " : " + p.getIndex());
        }

        boolean[] visited = new boolean[arr.length];
        int minSwaps = 0;
        for(int i = 0; i < arr.length; i++) {

            int old_position = elementIndexMap[i].getIndex();
            if(visited[i] || old_position == i) {
                continue;
            }

            int j = i;
            int cnt = 0;
            while(!visited[j]) {
                visited[j] = true;
                cnt++;
                j = elementIndexMap[j].getIndex();
            }
            minSwaps += (cnt - 1);
        }

        return minSwaps;
    }

    public class Pair {
        private int element;
        private int index;

        public Pair(int element, int index) {
            this.element = element;
            this.index = index;
        }

        public int getElement() {
            return element;
        }
    
        public int getIndex() {
            return index;
        }
    }

    public int maxSubarraySum(int[] arr) {
        int n = arr.length;

        int currSum = 0;
        int maxSum = 0;
        // Kadane's algo
        for(int i = 0; i < n; i++) {
            currSum = Math.max(0, currSum + arr[i]);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }

    public int[] minDiffPair(int[] arr1, int[] arr2) {
        int[] pair = new int[2];
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        int m = arr1.length;
        int n = arr2.length;
        int i = 0, j = 0;
        int minDiff = Integer.MAX_VALUE;
        while (i < m && j < n) {
            int n1 = arr1[i];
            int n2 = arr2[j];
            if(minDiff > Math.abs(n1 - n2)) {
                pair[0] = n1;
                pair[1] = n2;
                minDiff = Math.abs(n1 - n2);
            }
            if(n1 < n2) {
                i++;
            } else if(n1 > n2) {
                j++;
            } else {
                return new int[] {n1, n2};
            }
        }

        return pair;
    }

    public int[] productArray(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        // calculate left and right product
        int leftProduct = 1;
        for(int i = 0; i < n; i++) {
            result[i] = leftProduct;
            leftProduct *= arr[i];
        }

        int rightProduct = 1;
        for(int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= arr[i];
        }

        return result;
    }

    public int maxActivities(int[][] activities) {
        Arrays.sort(activities, (int[] a, int[] b) -> Integer.compare(a[1], b[1]));

        int maxActivities = 0;
        int lastEndTime = 0;
        for(int[] activity : activities) {
            int start = activity[0];
            int end = activity[1];

            if(start >= lastEndTime) {
                maxActivities++;
                lastEndTime = end;
            }
        }

        return maxActivities;
    }

    public static void main(String[] args) {
        ArrayProbs arrP = new ArrayProbs();

        // Problem 1: Pair sum
        int[] arr1 = new int[] {10, 5, 2, 3, -6, 9, 11};
        int[] resultPair = arrP.pairWithSum(arr1, 4);
        System.out.println("Pair with sum 4: " + resultPair[0] + ", " + resultPair[1]);

        // Problem 2: Triplets with given sum
        int[] arr2 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 15};
        ArrayList<int[]> tripletSets = arrP.tripletsWithSum(arr2, 18);
        System.out.println("Triplets are: ");
        for(int[] r : tripletSets) {
            System.out.println(r[0] + ", " + r[1] + ", " + r[2]);
        }

        // Problem 3: Mountain
        int[] arr3 = new int[] {5, 6, 1, 2, 3, 4, 5, 4, 3, 2, 0, 1, 2, 3, -2, 4};
        System.out.println("Longest mountain is of length: " + arrP.mountainLen(arr3));

        // Problem 4: Longest Band
        int[] arr4 = new int[] {1, 9, 3, 0, 18, 5, 2, 4, 10, 7, 12, 6};
        System.out.println("Longest band of consecutive integers: " + arrP.longestBand(arr4));

        // Problem 5: Rains 
        int[] arr5 = new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] arr6 = new int[] {0, 1};
        System.out.println("Rain water trapped (units): " + arrP.rainTrapped(arr5));

        // Problem 6: Subarray sort
        int[] arr7 = new int[] {1, 2, 3, 4, 5, 8, 6, 7, 9, 10, 11};
        int[] sortIdx = arrP.subarraySort(arr7);
        System.out.println("Subarray must be sorted within indexes: " + sortIdx[0] + ", " + sortIdx[1]);

        // Problem 7: Minimum swaps
        int[] arr8 = new int[] {5, 4, 3, 2, 1};
        int minSwaps = arrP.minSwaps(arr8);
        System.out.println("Minimum number of swaps to sort array: " + minSwaps);

        // Problem 8: Maximum subarray sum
        int[] arr9 = new int[] {-1,2,3,4,-2,6,-8,3};
        int maxSum = arrP.maxSubarraySum(arr9);
        System.out.println("Maximum subarray sum for arr is: " + maxSum);

        // Problem 9: Minimum difference pair
        int[] arr10 = new int[] {23, 5, 10, 17, 30};
        int[] arr11 = new int[] {26, 134, 135, 14, 19};
        int[] pair = arrP.minDiffPair(arr10, arr11);
        System.out.println("The pair with minimum diff: " + pair[0] + ", " + pair[1]);

        // Problem 10: Product array
        int[] arr12 = new int[] {1,2,3,4,5};
        int[] productArr = arrP.productArray(arr12);
        System.out.println("The product array is:");
        for(int i = 0; i < productArr.length; i++)
            System.out.print(productArr[i] + " ");
        System.out.println();

        // Problem 11: Busy Life
        int[][] activities = {{7, 9}, {0, 10}, {4, 5}, {8, 9}, {4, 10}, {5, 7}};
        // greedy approach
        System.out.println("Maximum number of activities that can be done: " + arrP.maxActivities(activities));
    }
}
