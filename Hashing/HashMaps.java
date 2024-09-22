package Hashing;

import java.util.List;
import Hashing.HashMaps.Coords;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class HashMaps {
    
    public int countGPTriplets(int[] arr, int r) {
        HashMap<Integer, Integer> leftFreq = new HashMap<>();
        HashMap<Integer, Integer> rightFreq = new HashMap<>();

        for(int a : arr) {
            rightFreq.put(a, rightFreq.getOrDefault(a, 0) + 1);
        }

        int cnt = 0;
        for(int i = 0; i < arr.length - 1; i++) {
            int n = arr[i];
            if(rightFreq.get(n) > 1) {
                rightFreq.put(n, rightFreq.get(n) - 1);
            } else {
                rightFreq.remove(n);
            }
            int nLeft = leftFreq.getOrDefault(n/r, 0);
            int nRight = rightFreq.getOrDefault(n*r, 0);
            if(n%r == 0 &&  nLeft > 0 && nRight > 0) {
                cnt += (nLeft * nRight);
            }
            leftFreq.put(n, leftFreq.getOrDefault(n, 0) + 1);
        }

        return cnt;
    }

    public static class Coords {
        int x, y;

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) return true;
            if(!(o instanceof Coords)) return false;

            Coords c = (Coords) o;
            return x == c.x && y == c.y;
        }

        @Override
        public int hashCode() {
            return x * 1000 + y;
        }
    }

    public int countRectangles(ArrayList<Coords> p) {
        int N = p.size();

        HashSet<Coords> exists = new HashSet<>();
        exists.addAll(p);

        int cnt = 0;
        for(int i = 0; i < N - 2; i++) {
            for(int j = i+1; j < N; j++) {
                Coords p1 = p.get(i);
                Coords p2 = p.get(j);

                if(p1.x == p2.x || p1.y == p2.y) continue;

                Coords p3 = new Coords(p1.x, p2.y);
                Coords p4 = new Coords(p2.x, p1.y);

                //System.out.printf("P1: x=%d, y=%d; P2: x=%d, y=%d; P3: x=%d, y=%d; P4: x=%d, y=%d\n", p1.x,p1.y,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y);
                if(exists.contains(p3) && exists.contains(p4)) {
                    cnt += 1;
                }
            }
        }

        return cnt/2;
    }

    public int countTriangles(ArrayList<Coords> p) {
        int N = p.size();
        HashMap<Integer, Integer> xFreq = new HashMap<>();
        HashMap<Integer, Integer> yFreq = new HashMap<>();

        for(Coords c : p) {
            xFreq.put(c.x, xFreq.getOrDefault(c.x, 0) + 1);
            yFreq.put(c.y, yFreq.getOrDefault(c.y, 0) + 1);
        }

        int cnt = 0;
        for(Coords c : p) {
            int fx = xFreq.get(c.x);
            int fy = yFreq.get(c.y);

            cnt += (fx - 1) * (fy - 1);
        }

        return cnt;
    }

    public List<Integer> getHashKey(String s, int i, int j) {
        int[] hashKey = new int[26];
        for(int k = i; k <= j; k++) {
            char ch = s.charAt(k);
            hashKey[ch - 'a']++;
        }
        List<Integer> hashKeyList = new ArrayList<>();
        for (int key : hashKey) {
            hashKeyList.add(key);
        }

        return hashKeyList;
    }

    public int countAnagramPairs(String s) {
        HashMap<List<Integer>, Integer> map = new HashMap<>();

        for(int i = 0; i < s.length(); i++) {
            for(int j = i; j < s.length(); j++) {
                List<Integer> hk = getHashKey(s, i, j);
                map.put(hk, map.getOrDefault(hk, 0) + 1);
            }
        }

        int ans = 0;
        for(List<Integer> hashKey : map.keySet()) {
            int freq = map.get(hashKey);
            if(freq >= 2) {
                ans += freq*(freq - 1)/2;
            }
        }

        return ans;
    }

    public HashMap<List<Integer>, ArrayList<String>> groupAnagrams(String[] words) {
        HashMap<List<Integer>, ArrayList<String>> result = new HashMap<>();

        for(String word : words) {
            List<Integer> key = getHashKey(word, 0, word.length() - 1);
            ArrayList<String> wordList = result.getOrDefault(key, new ArrayList<String>());
            wordList.add(word);
            result.put(key, wordList);
        }

        return result;
    }

    public int minPartitions(String s, String[] words) {
        HashSet<String> set = new HashSet<>();
        for(String word : words) {
            set.add(word);
        }
        return minPartitionsHelper(s, 0, set) - 1;
    }

    public int minPartitionsHelper(String str, int i, HashSet<String> exists) {
        // base case
        if(i >= str.length()) {
            return 0;
        }

        // rec case
        int ans = Integer.MAX_VALUE;
        String curr = "";
        for(int j = i; j < str.length(); j++) {
            curr += str.charAt(j);
            //System.out.println(curr);

            if(exists.contains(curr)) {
                int currAns = minPartitionsHelper(str, j + 1, exists);
                if(currAns != -1) {
                    ans = Math.min(ans, currAns + 1);
                }
            }
        }

        return (ans == Integer.MAX_VALUE) ? -1 : ans;
    }

    public int minPartitionsDP(String s, String[] words) {
        int[] dp = new int[s.length()];
        HashSet<String> set = new HashSet<>();
        for(String word : words) {
            set.add(word);
        }
        minPartitionsDPHelper(s, 0, set, dp);

        return dp[0] - 1;
    }

    public int minPartitionsDPHelper(String str, int i, HashSet<String> exists, int[] dp) {
        // base case
        if(i >= str.length()) {
            return 0;
        }

        if(dp[i] != 0) {
            return dp[i];
        }

        // rec case
        int ans = Integer.MAX_VALUE;
        String curr = "";
        for(int j = i; j < str.length(); j++) {
            curr += str.charAt(j);
            if(exists.contains(curr)) {
                int currAns = minPartitionsDPHelper(str, j + 1, exists, dp);
                if(currAns != -1) {
                    ans = Math.min(ans, currAns + 1);
                }
            }
        }

        dp[i] = (ans == Integer.MAX_VALUE) ? -1 : ans;
        //System.out.println(i + " -> " + str.substring(i, str.length()) + " -> " + dp[i]);
        return dp[i];
    }

    public static class Node {
        int key;
        Node next;

        public Node(int k) {
            key = k;
            next = null;
        }
    }

    public Node breakChain(Node head) {
        HashSet<Node> exists = new HashSet<>();
        exists.add(head);
        
        Node temp = head;
        while(temp.next != null && !exists.contains(temp.next)) {
            temp = temp.next;
            exists.add(temp);
        }
        temp.next = null;

        return head;
    }

    public int longestKSumSubarray(int[] arr, int k) {
        HashMap<Integer, Integer> sumIndexMap = new HashMap<>();

        int maxLen = 0;
        int prefixSum = 0;

        for(int i = 0; i < arr.length; i++) {
            // calculate prefix sum
            prefixSum += arr[i];

            // check if prefix sum == k
            if(prefixSum == k) {
                maxLen = i + 1;
            }
            else if(sumIndexMap.containsKey(prefixSum - k)) { // else we need to check if there exists a sum with prefixSum - k
                maxLen = Math.max(maxLen, i - sumIndexMap.get(prefixSum - k));
            }

            // we are skipping re-inserting because we need lowest index for a calculated sum
            sumIndexMap.putIfAbsent(prefixSum, i);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        HashMaps hm = new HashMaps();

        // Problem 1: Count triplets of GP such that i < j < k (indices)
        int[] arr = {1, 16, 4, 16, 64, 16};
        int r = 4;
        System.out.println("Count of GP triplets: " + hm.countGPTriplets(arr, r));

        // Problem 2: Count number of axis parallel rectangles
        ArrayList<Coords> points = new ArrayList<Coords>();
        points.add(new Coords(0, 0));
        points.add(new Coords(0, 1));
        points.add(new Coords(1, 1));
        points.add(new Coords(1, 0));
        points.add(new Coords(3, 1));
        points.add(new Coords(3, 0));
        points.add(new Coords(2, 0));
        points.add(new Coords(2, 1));
        System.out.println("Number of rectangles formed are: " + hm.countRectangles(points));

        // Problem 3: Count number of right angled triangles with base or perpendicular parallel to x or y axis
        ArrayList<Coords> points2 = new ArrayList<Coords>();
        points2.add(new Coords(1, 2));
        points2.add(new Coords(2, 1));
        points2.add(new Coords(2, 2));
        points2.add(new Coords(2, 3));
        points2.add(new Coords(3, 2));
        System.out.println("Number of triangles formed are: " + hm.countTriangles(points2));

        // Problem 4: Count anagram pairs in a given string
        System.out.println("Count of anagram pairs for 'abba' : " + hm.countAnagramPairs("abba"));
        System.out.println("Count of anagram pairs for 'abcd' : " + hm.countAnagramPairs("abcd"));

        // Problem 5: Count the minimum number of partitions in the sentence formed using given words
        String[] words = {
            "the",
            "fox",
            "thequickbrownfox",
            "jumps",
            "lazy",
            "lazyfox",
            "highbridge",
            "the",
            "over",
            "bridge",
            "high",
            "tall",
            "quick",
            "brown"
        };
        String str = "thequickbrownfoxjumpsoverthehighbridge";
        System.out.println("Minimum number of partitions in the string: " + hm.minPartitions(str, words));

        // Problem 6: Break the chain (hashing based)
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = head.next;

        System.out.print("Linked list after chain break: ");
        Node node = hm.breakChain(head);
        while(node != null) {
            System.out.print(node.key + " ");
            node = node.next;
        }
        System.out.println();

        // Problem 7: Minimum partitions using top down DP
        System.out.println("Minimum number of partitions using DP in the string: " + hm.minPartitionsDP(str, words));

        // Problem 8: Group anagrams
        String[] strs = {
            "eat",
            "tea",
            "nat",
            "tan",
            "ate",
            "bat",
        };

        HashMap<List<Integer>, ArrayList<String>> groupedAnagrams = hm.groupAnagrams(strs);
        System.out.println("Grouped anagrams: ");
        for(ArrayList<String> group : groupedAnagrams.values()) {
            for(String word : group) {
                System.out.print(word + " ");
            }
            System.out.println();
        }

        // Problem 9: Longest subarray with K-sum
        int[] a1 = { 10, 5, 2, 7, 1, 9 };
        System.out.println("Longest K-sum subarray is of length: " + hm.longestKSumSubarray(a1, 15));
    }
}
