package Graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class GraphWithNode {
    private class Node {
        String name;
        LinkedList<String> nbrs;

        private Node(String n) {
            name = n;
            nbrs = new LinkedList<String>();
        }
    }

    HashMap<String, Node> graph = new HashMap<>();
    boolean isDirected = false;

    public GraphWithNode(String[] names, boolean isDir) {
        for(String name : names) {
            graph.put(name, new Node(name));
        }
        isDirected = isDir;
    }

    public void addEdge(String x, String y) {
        graph.get(x).nbrs.add(y);

        if(!isDirected) {
            graph.get(y).nbrs.add(x);
        }
    }

    public void printGraph() {
        for(String node : graph.keySet()) {
            System.out.print(node + " : ");
            for(String nbrs : graph.get(node).nbrs) {
                System.out.print(nbrs + " ");
            }
            System.out.println();
        }
    }

    public void bfs(String source) {
        Queue<Node> q = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();

        Node sourceNode = graph.get(source);
        q.offer(sourceNode);
        visited.add(source);

        while(!q.isEmpty()) {
            Node curr = q.poll();
            System.out.print(curr.name + " -> ");
            
            for(String nbr : curr.nbrs) {
                if(!visited.contains(nbr)) {
                    q.offer(graph.get(nbr));
                    visited.add(nbr);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] cities = new String[] {
            "Delhi",
            "London",
            "Paris",
            "New York"
        };

        GraphWithNode graph = new GraphWithNode(cities, true);
        graph.addEdge("Delhi", "London");
        graph.addEdge("New York", "London");
        graph.addEdge("Delhi", "Paris");
        graph.addEdge("Paris", "New York");

        graph.printGraph();
        System.out.println("The BFS of the graph is : ");
        graph.bfs("Delhi");
    }
}
