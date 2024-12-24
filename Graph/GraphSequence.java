package Graph;

public class GraphSequence {
    public void dfs(int[][] matrix, boolean[][] visited, int i, int j, int[][] cache) {
        visited[i][j] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int cnt = 0;
        for(int k = 0; k < 4; k++) {
            int nx = i + dx[k];
            int ny = j + dy[k];

            if(nx >= 0 && ny >= 0 && nx < matrix.length && ny < matrix[0].length && matrix[nx][ny] > matrix[i][j]) {
                if(!visited[nx][ny]) {
                    dfs(matrix, visited, nx, ny, cache);
                }
                cnt = Math.max(cnt, 1 + cache[nx][ny]);
            }
        }
        cache[i][j] = cnt;
        return;
    }

    public int longestPathSequence(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] cache = new int[m][n];
        boolean[][] visited = new boolean[m][n];

        int ans = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                dfs(matrix, visited, i, j, cache);
                ans = Math.max(ans, cache[i][j]);
            }
        }

        return ans + 1;
    }

    public static void main(String[] args) {
        GraphSequence gs = new GraphSequence();
        int[][] matrix = {
            { 0,  2,  4,  3,  2 },
            { 7,  6,  5,  5,  1 },
            { 8,  9,  7, 18, 14 },
            { 5, 10, 11, 12, 13 },
        };
        
        System.out.println("Longest chain of increasing number: " + gs.longestPathSequence(matrix));
    }
}
