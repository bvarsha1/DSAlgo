package Graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class ShortestGridPath {

    private class Node {
        int row, col, dist;
        private Node(int r, int c, int d) {
            row = r;
            col = c;
            dist = d;
        }
    }

    int rows, cols;
    int[][] grid;

    public ShortestGridPath(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = grid[0].length;
    }

    public int[][] minCost() {
        // 2-D matrix to denote the minimum distance from top left to all the cells
        int[][] distance = new int[rows][cols];
        for(int[] distRow : distance)
            Arrays.fill(distRow, Integer.MAX_VALUE);

        int r = 0, c = 0;
        distance[r][c] = grid[r][c];
        PriorityQueue<Node> pq = new PriorityQueue<>( (a, b) -> (Integer.compare(a.dist, b.dist)) );
        pq.offer(new Node(r, c, distance[r][c]));

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        // dijkshtra's
        while(!pq.isEmpty()) {
            Node curr = pq.poll();

            for(int i = 0; i < 4; i++) {
                int nr = curr.row + dx[i];
                int nc = curr.col + dy[i];

                // check if in-bounds
                if(nr >= 0 && nc >= 0 && nr < rows && nc < cols) {
                    if(curr.dist + grid[nr][nc] < distance[nr][nc]) {
                        Predicate<Node> pred = (a) -> (a.row == nr && a.col == nc);
                        pq.removeIf(pred);
                        distance[nr][nc] = curr.dist + grid[nr][nc];
                        pq.offer(new Node(nr, nc, distance[nr][nc]));
                    }
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {
            {31, 100, 65, 12, 18},
            {10, 13, 47, 157, 6},
            {100, 113, 174, 11, 33},
            {88, 124, 41, 20, 140},
            {99, 32, 111, 41, 20}
        };
        ShortestGridPath sgp = new ShortestGridPath(grid);
        
        int[][] distance = sgp.minCost();
        System.out.println("The shortest distance from top left to bottom right : " + distance[sgp.rows - 1][sgp.cols - 1]);
    }
}
