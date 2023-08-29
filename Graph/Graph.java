package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    /*
     * Adjacency Lists vs Adjacency Matrix
     *  Adj List advantages :
     *      1. Less time to find all the edges of a vertex
     *      2. Less memory for sparse graph O(V + E)
     *  Adj List disadvantage :
     *      1. More time to find if an edge exists between 2 vertices, should traverse entire list of edges
     *      2. Can be used for different data types
     * 
     *  Adj Matrix advantages :
     *      1. O(1) time to check if an edge exists between 2 vertices
     *  Adj Matrix disadvantages :
     *      1. O(v) time to find all the edges of a given vertex
     *      2. Might contribute to memory wastage in case of sparse graph O(V^2)
     */

    int V;
    ArrayList<LinkedList<Integer>> adjList;
    boolean isDirected = false;

    public Graph(int v, boolean isDir){
        V = v;
        adjList = new ArrayList<>(v);
        for(int i = 0; i < v; i++) {
            adjList.add(i, new LinkedList<Integer>());
        }
        // defaulted to false
        isDirected = isDir;
    }

    public void addEdge(int i, int j) {
        adjList.get(i).add(j);
        if(!isDirected)
            adjList.get(j).add(i);
    }

    public void printGraph() {
        for(int i = 0; i < V; i++) {
            System.out.print(i + " : ");
            for(int node : adjList.get(i)) {
                System.out.print(node + " ");
            }
            System.out.println();
        }
    }

    public void bfs(int source) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);

        q.offer(source);
        visited[source] = true;

        while(!q.isEmpty()) {
            int curr = q.poll();
            System.out.print(curr + " -> ");
            // visit neighboring nodes, add them to queue if not visited, and mark visited
            for(int nbr : adjList.get(curr)) {
                if(!visited[nbr]) {
                    q.offer(nbr);
                    visited[nbr] = true;
                }
            }
        }
        System.out.println();
    }

    public void shortestDistance(int source, int destination) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);

        // distance and parent trackers
        int[] dist = new int[V];
        Arrays.fill(dist, 0);

        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        q.offer(source);
        visited[source] = true;

        while(!q.isEmpty()) {
            int curr = q.poll();
            // visit neighboring nodes, add them to queue if not visited, and mark visited
            for(int nbr : adjList.get(curr)) {
                if(!visited[nbr]) {
                    q.offer(nbr);
                    parent[nbr] = curr;
                    dist[nbr] = dist[curr] + 1;
                    visited[nbr] = true;
                }
            }
        }
        
        for(int i = 0; i < V; i++) {
            System.out.println("Shortest distance from " + source + " to " + i + " is : " + dist[i]);
        }

        System.out.print("The shortest path from source " + source + " to destination " + destination + " : ");
        if(destination != -1) {
            int temp = destination;
            while(temp != source) {
                System.out.print(temp + " -> ");
                temp = parent[temp];
            }
            System.out.print(temp);
        }
        System.out.println();
    }

    public void dfs(int source) {
        boolean[] visited = new boolean[V];
        System.out.print("The dfs traversal of the graph is : ");
        dfsHelper(source, visited);
        System.out.println();
    }

    public void dfsHelper(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node  + " -> ");
        
        for(int nbr : adjList.get(node)) {
            if(!visited[nbr])
                dfsHelper(nbr, visited);
        }
    }

    public boolean containsCycleUndirected(int node, boolean[] visited, int parent) {
        visited[node] = true;

        for(int nbr : adjList.get(node)) {
            if(!visited[nbr]) {
                // we cannot blindly return false if nbr doesnt contain cycle,
                // we have to move upward in the call stack
                boolean nbrContainsCycle = containsCycleUndirected(nbr, visited, node);
                if(nbrContainsCycle)
                    return true;
            } else if(nbr != parent) {
                // if visited and neighbour is not equal to parent
                return true;
            }
        }

        // if none of the above conditions are met, we return false
        return false;
    }

    public boolean containsCycleDirected(int node, boolean[] visited, boolean[] existsInPath) {
        visited[node] = true;
        existsInPath[node] = true;

        for(int nbr : adjList.get(node)) {
            // we dont care about visited at this point, but only the call trace path
            if(existsInPath[nbr]) {
                return true;
            } else if(!visited[nbr]) {
                boolean nbrContainsCycle = containsCycleDirected(nbr, visited, existsInPath);
                if(nbrContainsCycle) {
                    return true;
                }
            }
        }
        // going back
        existsInPath[node] = false;
        return false;
    }

    public boolean containsCycle() {
        // assuming the graph is connected
        boolean[] visited = new boolean[V];
        if(!isDirected) {
            return containsCycleUndirected(0, visited, -1);
        }
        return containsCycleDirected(0, visited, new boolean[V]);
    }

    public int shortestCycleContainingNode(int node) {
        boolean[] visited = new boolean[V];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int[] parents = new int[V];
        Arrays.fill(parents, -1);

        q.offer(node);
        int len = 0;
        
        while(!q.isEmpty()) {
            len++;
            // check all the items in the list, this list will be at the same level
            int qSize = q.size();
            for(int i = 0; i < qSize; i++) {
                int curr = q.poll();
                // for all neighbors
                for(int nbr : adjList.get(curr)) {
                    if(visited[nbr] && nbr != node)
                        continue;
                    parents[nbr] = curr;
                    if(nbr == node) {
                        return len;
                    }
                    visited[nbr] = true;
                    q.offer(nbr);
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        // Create graph and add edge
        Graph graph = new Graph(6, false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(2, 1);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(2, 3);
        graph.addEdge(3, 5);
        graph.printGraph();

        // Create graph and print BFS
        Graph g2 = new Graph(7, false);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(3, 5);
        g2.addEdge(5, 6);
        g2.addEdge(4, 5);
        g2.addEdge(0, 4);
        g2.addEdge(3, 4);
        g2.bfs(1);

        // Print shortest distance from source to all nodes
        // Shortest path from source to destination
        g2.shortestDistance(1, 6);

        // Problem 1 : min throws - Snakes and Ladders
            // do in leetcode

        // Print DFS
        g2.dfs(1);

        // Problem 2 : cycle detection in undirected graph
        Graph g3 = new Graph(3, false);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        System.out.println("Does the graph contain a cycle? Answer : " + g3.containsCycle());

        // Problem 3 : cycle detection in directed graph
        Graph g4 = new Graph(3, true);
        g4.addEdge(1, 2);
        g4.addEdge(2, 0);
        g4.addEdge(0, 1);
        System.out.println("Does the graph contain a cycle? Answer : " + g4.containsCycle());

        // Problem 4 : shortest cycle containing a given node
        Graph g5 = new Graph(5, true);
        g5.addEdge(0, 1);
        g5.addEdge(0, 4);
        g5.addEdge(1, 2);
        g5.addEdge(2, 3);
        g5.addEdge(3, 1);
        g5.addEdge(3, 0);
        g5.addEdge(4, 3);
        // Hints : shortest path - BFS / containing node - we can start from the node
        System.out.println("Length of shortest cycle containing a graph : " + g5.shortestCycleContainingNode(3));
    }
}
