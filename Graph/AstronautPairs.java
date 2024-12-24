package Graph;

import java.util.*;

public class AstronautPairs {
    public static void main(String[] args) {
        int n = 5;
        int[][] astronauts = {{0, 1}, {2, 3}, {0, 4}};
        System.out.println("Number of valid pairs: " + countPairs(n, astronauts));
    }

    public static int countPairs(int n, int[][] astronauts) {
        // Initialize Union-Find structure
        UnionFind uf = new UnionFind(n);

        // Process pairs to union astronauts from the same country
        for (int[] pair : astronauts) {
            uf.union(pair[0], pair[1]);
        }

        // Count the size of each connected component
        Map<Integer, Integer> componentSizes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            componentSizes.put(root, componentSizes.getOrDefault(root, 0) + 1);
        }

        // Calculate total pairs and subtract pairs within the same country
        long totalPairs = (long) n * (n - 1) / 2; // Total pairs
        long sameCountryPairs = 0;

        for (int size : componentSizes.values()) {
            sameCountryPairs += (long) size * (size - 1) / 2; // Pairs within the same component
        }

        return (int) (totalPairs - sameCountryPairs);
    }
}

// Union-Find (Disjoint Set Union) Implementation
class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; // Each node is its own parent initially
            rank[i] = 1;   // Rank starts at 1
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            // Union by rank
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
