package Graph;

import java.util.Arrays;

public class LargestIsland {
    int rows, cols;
    int[][] grid;

    public LargestIsland(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = grid[0].length;
    }

    public int largestIsland() {
        boolean[][] visited = new boolean[rows][cols];
        for(boolean[] visitedRow : visited)
            Arrays.fill(visitedRow, false);

        int max = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 1 && !visited[i][j]) {
                    int currSize = largestIslandHelper(i, j, visited);
                    max = Math.max(currSize, max);
                }
            }
        }
        return max;
    }

    public int largestIslandHelper(int i, int j, boolean[][] visited) {
        visited[i][j] = true;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        int currSize = 1;
        for(int k = 0; k < 4; k++) {
            int x = i + dx[k], y = j + dy[k];
            // check if in-bounds and is not visited
            if(x >= 0 && y >= 0 && x < rows && y < cols && grid[x][y] == 1 && !visited[x][y]) {
                currSize += largestIslandHelper(x, y, visited);
            }
        }
        return currSize;
    }

    public static void main(String[] args) {
        int[][] grid = {
            { 1, 0, 0, 1, 0 },
            { 1, 0, 1, 0, 0 },
            { 1, 1, 1, 0, 0 },
            { 1, 0, 1, 1, 1 },
            { 1, 0, 1, 1, 0 }
        };
        LargestIsland li = new LargestIsland(grid);

        System.out.println("The largest island is of size : " + li.largestIsland());
    }
}
