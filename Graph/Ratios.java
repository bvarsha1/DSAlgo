package Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Ratios {
    public int calcRatio(char num, char den) {
        Set<Character> visited = new HashSet<>();
        if(!graph.containsKey(num)) return Integer.MIN_VALUE;

        return dfs(num, den, 1, visited);
    }

    public int dfs(char curr, char target, int ans, Set<Character> visited) {
        if(curr == target) return ans;
        visited.add(curr); // to avoid cycle in case it exists in the path

        if(graph.containsKey(curr)) {
            for(Map.Entry<Character, Integer> nbr : graph.get(curr).entrySet()) {
            
                if(!visited.contains(nbr)) {
                    int res = dfs(nbr.getKey(), target, ans * nbr.getValue() , visited);
                    if(res != Integer.MIN_VALUE) return res;
                }
            }
        }

        visited.remove(curr); // backtrack
        return Integer.MIN_VALUE;
    }

    Map<Character, Map<Character, Integer>> graph = new HashMap<>();
    public static void main(String[] args) {
        Ratios r = new Ratios();
        r.addEdge('a', 'b', 10);
        r.addEdge('b', 'c', 20);
        r.addEdge('b', 'e', -1);
        r.addEdge('e', 'f', -10);
        System.out.println("Ratio: " + r.calcRatio('a', 'f'));
        System.out.println("Ratio: " + r.calcRatio('a', 'c'));
    }

    public void addEdge(char from, char to, int w) {
        graph.putIfAbsent(from, new HashMap<>());
        graph.get(from).put(to, w);
        // should we handle reverse edge - or is the graph 1 side directed
    }
}
