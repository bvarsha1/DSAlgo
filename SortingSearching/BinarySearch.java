package SortingSearching;

import java.text.DecimalFormat;
import java.util.*;

public class BinarySearch {
    
    private int upperBound(int[] nums, int key) {
        int l = 0, r = nums.length - 1;
        int ans = -1;
        while(l <= r) {
            int m = (l + r) / 2;
            if(nums[m] == key) {
                ans = m;
                l = m + 1;
            } else if(nums[m] > key) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return ans;
    }

    private int lowerBound(int[] nums, int key) {
        int l = 0, r = nums.length - 1;
        int ans = -1;
        while(l <= r) {
            int m = (l + r) / 2;
            if(nums[m] == key) {
                ans = m;
                r = m - 1;
            } else if(nums[m] > key) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return ans;
    }

    private int lowerBound2(int[] nums, int key) {
        int l = 0, r = nums.length - 1;
        int ans = -1;
        while(l <= r) {
            int m = (l + r) / 2;
            if(nums[m] == key) {
                return m;
            } else if(nums[m] > key) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return l;
    }

    public int frequencyCount(int[] nums, int key) {
        return upperBound(nums, key) - lowerBound(nums, key) + 1;
    }

    public int rotatedSearch(int[] nums, int key) {
        int l = 0, r = nums.length - 1;

        while(l <= r) {
            int m = (l + r) / 2; // l + (r - l) / 2; - to avoid overflow
            if(nums[m] == key) {
                return m;
            } else if(nums[l] <= nums[m]) {
                // we now know its on line 1
                // choose where to search
                if(nums[l] <= key  && key < nums[m]) {
                    // go left
                    r = m - 1;
                } else {
                    // go right
                    l = m + 1;
                }
            } else {
                // we know its on line 2
                // chose which part to search
                if(nums[m] < key && key <= nums[r]) {
                    // go right
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }

        return -1;
    }

    public float squareRoot(int n, int p) {
        int l = 0, r = n;
        float ans = 0.0f;

        // Binary Search for Integer part
        while(l <= r) {
            int m = (l + r) / 2;
            if(m * m == n) {
                return m;
            } else if(m * m < n) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        // Linear Search for decimal
        float inc = 0.1f;
        for(int i = 1; i <= p; i++) {
            // do linear search
            while(ans * ans <= n) {
                ans += inc;
            }
            ans = ans - inc;
            inc /= 10f;
        }

        return ans;
    }

    public int[] minPair(int[] a1, int[] a2) {
        if(a1.length < a2.length) {
            return minPairHelper(a2, a1);
        }
        
        return minPairHelper(a1, a2);
    }

    public int[] minPairHelper(int[] a1, int[] a2) {
        Arrays.sort(a2);

        int[] ans = new int[2];
        int diff = Integer.MAX_VALUE;

        // iterate over the other to look for closest elements using BS
        for(int x : a1) {
            int lb = lowerBound2(a2, x);
            if(lb == -1) continue;

            // left comparison
            if(lb > 0 && x - a2[lb -1] < diff) {
                diff = x - a2[lb - 1];
                ans[0] = x;
                ans[1] = a2[lb - 1];
            }

            if(lb != a2.length && a2[lb] - x < diff) {
                diff = a2[lb] - x;
                ans[0] = a2[lb];
                ans[1] = x;
            }
        }

        return ans;
    }

    public boolean divideAmongK(int[] a, int n, int k, int min) {
        int paritions = 0;
        int currSum = 0;

        for(int i = 0; i < n; i++) {
            if(currSum + a[i] >= min) {
                paritions++;
                // reset curr sum
                currSum = a[i];
            } else {
                currSum += a[i];
            }
        }

        return paritions >= k;
    }
    
    public int kPartition(int[] a, int n, int k) {
        int s = 0;
        int e = 0;
        for(int x : a) {
            s = Math.min(s, x);
            e += x;
        }

        int ans = 0;
        while(s <= e) {
            int m = (s + e) / 2;
            boolean isPossible = divideAmongK(a, n, k, m);

            if(isPossible) {
                s = m + 1;
                ans = m;
            } else {
                e = m - 1;
            }
        }

        return ans;
    }

    public int getCoins(int[] coins, int k) {
        return kPartition(coins, coins.length, k);
    }

    public int minPages(int[] books, int students) {
        int s = 0, e = 0;
        for(int pages : books) {
            s = Math.max(s, pages);
            e += pages;
        }

        int ans = e;
        while(s <= e) {
            int m = (s + e) / 2;
            boolean isPossible = canDivideInStudents(books, books.length, students, m);

            if(isPossible) {
                ans = m;
                e = m - 1; // try for smaller max
            } else {
                s = m + 1; // increase threshold
            }
        }
        return ans;
    }

    public boolean canDivideInStudents(int[] books, int n, int students, int maxPages) {
        int curr = 1;
        int pageSum = 0;

        for(int pages : books) {
            if(pages + pageSum > maxPages) {
                curr++;
                pageSum = pages;
            } else {
                pageSum += pages;
            }
        }

        return curr <= students;
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();

        // Problem 1: Frequency count
        int[] nums = new int[] {0, 1, 1, 2, 3, 3, 3, 3, 3, 4, 5, 5, 5, 10};
        System.out.println("Freqeuency count of key in array : " + bs.frequencyCount(nums, 3));

        // Problem 2: Rotated search
        int[] nums1 = new int[] {7, 9, 10, 1, 2, 3, 4, 5, 6};
        System.out.println("Element occurs at index: " + bs.rotatedSearch(nums1, 10));

        // Problem 3: Square root
        DecimalFormat df = new DecimalFormat("#.###");
        String ans = df.format(bs.squareRoot(50, 3));
        System.out.println("Square root of number: " + ans);

        // Problem 4: Aggresive Cows / Angry Birds
        // TODO - binary search partitioning problem

        // Problem 5: Min pair code
        int[] a1 = {-1, 5, 10, 20, 3};
        int[] a2 = {26, 134, 135, 15,17};
        int[] mp = bs.minPair(a1, a2);
        System.out.println("Min pair is: [" + mp[0] + ", " + mp[1] + "]");

        // Problem 6: Game of Greed
        int k = 3;
        int[] coins = {10, 22, 40, 50};
        int[] coins2 = {};
        System.out.println("Max coins: " + bs.getCoins(coins, k));

        // Problem 7: Reading Books
        int[] books = {10, 20, 30, 15};
        System.out.println("Min books: " + bs.minPages(books, 2));
    }
}
