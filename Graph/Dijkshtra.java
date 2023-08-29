package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.function.Predicate;;

public class Dijkshtra {
    private class Pair {
        int node;
        int weight;

        private Pair(int n, int w) {
            node = n;
            weight = w;
        }
    }

    private class DistNode {
        int node;
        int dist;

        private DistNode(int n, int d) {
            node = n;
            dist = d;
        }
    }

    int V;
    ArrayList<LinkedList<Pair>> adjList;
    boolean isDirected = false;

    public Dijkshtra(int v, boolean isDir) {
        V = v;
        adjList = new ArrayList<>(v);
        for(int i = 0; i < V; i++) {
            adjList.add(i, new LinkedList<Pair>());
        }
        isDirected = isDir;
    }

    public void addEdge(int i, int j, int weight) {
        adjList.get(i).add(new Pair(j, weight));
        if(!isDirected) {
            adjList.get(j).add(new Pair(i, weight));
        }
    }

    public int[] shortestPath(int src, int dest) {
        // declarations
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);

        // key : distance; value : node
        PriorityQueue<DistNode> pq = new PriorityQueue<DistNode>( (a, b) -> Integer.compare(a.dist, b.dist) );

        // intilization
        distance[src] = 0;
        pq.offer(new DistNode(src, 0));

        while(!pq.isEmpty()) {
            DistNode curr = pq.poll();

            // traverse the neighbors
            for(Pair nbrPair : adjList.get(curr.node)) {
                int nbr = nbrPair.node;
                int edge = nbrPair.weight;

                if(curr.dist + edge < distance[nbr]) {
                    Predicate<DistNode> pred = a -> (a.node == nbr);
                    pq.removeIf(pred);
                    distance[nbr] = curr.dist + edge;
                    pq.offer(new DistNode(nbr, distance[nbr]));
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        Dijkshtra graph = new Dijkshtra(5, false);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(0, 2, 4);
        graph.addEdge(0, 3, 7);
        graph.addEdge(3, 2, 2);
        graph.addEdge(3, 4, 3);

        int src = 0;
        for(int i = 0; i < graph.V; i++)
            System.out.println("Distance between source " + src + " and destination " + i + " : " + graph.shortestPath(src, i)[i]);
    }
}