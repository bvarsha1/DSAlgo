package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import DisjointSets.DisjointSet;
import Graph.MinimumSpanningTree.Edge;

public class MinimumSpanningTree {

    public int indexWithMinWeight(int[] weights, boolean[] mstSet) {
        int minWeight = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < weights.length; i++) {
            if(mstSet[i] == false && weights[i] < minWeight) {
                minWeight = weights[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void printMST(int[] parent, int[][] graph) {
        for(int i = 1; i < parent.length; i++) {
            System.out.println(parent[i] + " -> " + i + "; Weight: " + graph[i][parent[i]]);
        }
    }

    public void PrimsAlgo(int[][] graph) {
        int V = graph.length;
        int[] parent = new int[V];
        int[] weights = new int[V];
        boolean[] mstSet = new boolean[V];

        Arrays.fill(weights, Integer.MAX_VALUE);
        parent[0] = -1;
        weights[0] = 0;
        for(int i = 0; i < V; i++) {
            int curr = indexWithMinWeight(weights, mstSet);
            mstSet[curr] = true;
            for(int j = 0; j < V; j++) {
                if(graph[i][j] != 0 && !mstSet[j] && graph[i][j] < weights[j]) {
                    weights[j] = graph[i][j];
                    parent[j] = i;
                }
            }
        }
        printMST(parent, graph);
    }

    static class Edge {
        int src, dest, weight;

        public Edge(int s, int d, int w) {
            this.src = s;
            this.dest = d;
            this.weight = w;
        }
    }

    public void printKruskalsMST(List<Edge> mstEdges) {
        for(Edge e : mstEdges) {
            System.out.println(e.src + " -> " + e.dest + "; Weight: " + e.weight);
        }
    }
    public void KruskalsAlgo(int V, List<Edge> graphEdges) {
        Collections.sort(graphEdges, (e1, e2) -> e1.weight - e2.weight);
        DisjointSet ds = new DisjointSet(V);
        int noOfEdges = 0;
        List<Edge> mstEdges= new ArrayList<>();
        for(Edge edge : graphEdges) {
            int x = ds.find(edge.src);
            int y = ds.find(edge.dest);
            if(x != y) {
                ds.union(x, y);
                noOfEdges++;
                mstEdges.add(edge);
            }
        }
        printKruskalsMST(mstEdges);
    }

    public static void main(String[] args) {
        MinimumSpanningTree mst = new MinimumSpanningTree();
        int[][] graph = new int[][] { { 0, 2, 0, 6, 0 },
                                      { 2, 0, 3, 8, 5 },
                                      { 0, 3, 0, 0, 7 },
                                      { 6, 8, 0, 0, 9 },
                                      { 0, 5, 7, 9, 0 } };

        System.out.println("Prim's MST is:");
        mst.PrimsAlgo(graph);
        System.out.println();

        int V = 4;
        List<Edge> graphEdges = new ArrayList<Edge>();
        graphEdges.add(new Edge(0, 1, 10));
        graphEdges.add(new Edge(0, 2, 6));
        graphEdges.add(new Edge(0, 3, 5));
        graphEdges.add(new Edge(1, 3, 15));
        graphEdges.add(new Edge(2, 3, 4));
        System.out.println("Kruskal's MST is:");
        mst.KruskalsAlgo(V, graphEdges);
    }
}
