package Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class TopologicalSort {

    int V;
    ArrayList<LinkedList<Integer>> adjList;

    public TopologicalSort(int v) {
        V = v;
        adjList = new ArrayList<LinkedList<Integer>>();
        for(int i = 0; i < v; i++) {
            adjList.add(i, new LinkedList<Integer>());
        }
    }

    public void addEdge(int i, int j) {
        adjList.get(i).add(j);
    }

    public boolean isCyclicHelper(int node, boolean[] visited, boolean[] existsInPath) {
        visited[node] = true;
        existsInPath[node] = true;

        for(int nbr : adjList.get(node)) {
            // check if it exists in path used to reach
            if(existsInPath[nbr]) {
                return true;
            } else if(!visited[nbr]) {
                boolean nbrIsCyclic = isCyclicHelper(nbr, visited, existsInPath);
                if(nbrIsCyclic)
                    return true;
            }
        }

        existsInPath[node] = false;
        return false;
    }

    public boolean isCyclic() {
        return isCyclicHelper(0, new boolean[V], new boolean[V]);
    }

    public void topoSortHelper(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;

        for(int nbr : adjList.get(node)) {
            if(!visited[nbr])
                topoSortHelper(nbr, visited, stack);
        }

        stack.push(node);
    }

    public int[] topoSort() {
        int[] result = new int[V];

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        for(int i = 0; i < V; i++) {
            if(!visited[i]) {
                topoSortHelper(i, visited, stack);
            }
        }

        for(int i = 0; i < V; i++) {
            result[i] = stack.pop();
        }

        return result;
    }

    public static void main(String[] args) {
        int numCourses = 2;
        int[][] prerequisites = { {1, 0} };
        TopologicalSort g = new TopologicalSort(numCourses);
        for(int[] pre : prerequisites) {
            g.addEdge(pre[1], pre[0]);
        }

        // check if cyclic
        if(g.isCyclic()) {
            System.out.println("The graph contains cycle, ordering not possible");
        }

        for(int e : g.topoSort()) {
            System.out.print(e + " -> ");
        }
    }
}
