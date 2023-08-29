package Graph;
import Tree.Trie;
import Tree.Trie.Node;

import java.util.Arrays;
import java.util.HashSet;

public class BoardGame {
    int rows, cols;

    public BoardGame(int r, int c) {
        rows = r;
        cols = c;
    }

    public void dfs(char[][] board, HashSet<String> wordExists, Node node, int row, int col, boolean[][] visited, String word) {
        char ch = board[row][col];
        // if trie doesn't contain the node, return
        if(!node.map.containsKey(ch)) {
            return;
        }

        // otherwise it contains the node
        // mark visited and move to this node
        visited[row][col] = true;
        node = node.map.get(ch);
        word += ch;

        // check if terminal
        if(node.isEnd)
            wordExists.add(word);

        int[] dx = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = new int[] {-1, -1, 0, 1, 1, 1, 0, -1};

        for(int i = 0; i < 8; i++) {
            int nRow = row + dx[i];
            int nCol = col + dy[i];

            // check if in bounds and not visited
            if(nRow >= 0 && nCol >= 0 && nRow < rows && nCol < cols && !visited[nRow][nCol]) {
                dfs(board, wordExists, node, nRow, nCol, visited, word);
            }
        }

        // backtracking step
        visited[row][col] = false;
    }

    public boolean[][] resetVisited() {
        boolean[][] visited = new boolean[rows][cols];
        for(boolean[] visitedRow : visited) {
            Arrays.fill(visitedRow, false);
        }

        return visited;
    }

    public static void main(String[] args) {
        String[] words = new String[] { "SNAKE", "FOR", "QUEZ", "SNACK", "SNACKS", "SENS", "TUNES", "CAT"};
        char[][] board = { {'S', 'E', 'R', 'T'},
                            {'U', 'N', 'K', 'S'},
                            {'T', 'C', 'A', 'T'} };
        BoardGame bg = new BoardGame(board.length, board[0].length);

        // create trie
        Trie t = new Trie();
        t.createTrie(words);

        // maintain a hashset of words encountered
        HashSet<String> wordExists = new HashSet<>();

        // run graph search
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                bg.dfs(board, wordExists, t.root, i, j, bg.resetVisited(), new String());
            }
        }

        // print result
        System.out.print("The words present in the grid : ");
        for(String word : wordExists) {
            System.out.print(word + " ");
        }
        System.out.println();
    }
}
