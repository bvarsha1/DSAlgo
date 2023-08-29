package DisjointSets;

import java.util.ArrayList;

public class DisjointSet {
    /*
     * Definition : Two or more sets with nothing in common are called disjoint sets
     *   { 1, 2, 3, 4, 5 } can be 2 sets like s1 = { 1, 2, 3 } & S2 = { 4, 5 }
     *   S1 insertion S2 is null
     * 
     * Uses :
     *  1. Keep track of the set that an element belongs to
     *      To check if 2 elements belong to the same set (FIND operation)
     *  2. Merge 2 sets into 1 (UNION operation)
     */

    public class Node {
        int parent = -1, rank = 0;
    }

    private static class Edge {
        int x, y;
        private Edge(int a, int b) {
            x = a;
            y = b;
        }
    }

    ArrayList<Node> dsuf;
    public DisjointSet(int size) {
        dsuf = new ArrayList<Node>(size);
        for(int i = 0; i< size; i++) {
            dsuf.add(i, new Node());
        }
    }

    // find operation
    public int find(int e) {
        if(dsuf.get(e).parent == -1)
            return e;
        dsuf.get(e).parent = find(dsuf.get(e).parent);
        return dsuf.get(e).parent;
    }

    public void union(int p, int q) {
        if(dsuf.get(p).rank > dsuf.get(q).rank) {
            dsuf.get(q).parent = p;
        } else if(dsuf.get(p).rank < dsuf.get(q).rank) {
            dsuf.get(p).parent = q;
        } else {
            dsuf.get(p).parent = q;
            dsuf.get(q).rank += 1;
        }
    }

    public boolean isCyclic(ArrayList<Edge> edgeList) {
        // iterate over each edge
        for( Edge e : edgeList) {
            int absX = find(e.x);
            int absY = find(e.y);

            // if at any point abs root of X and Y are the same, we return true
            if(absX == absY)
                return true;

            // else we union, to add the edge
            union(absX, absY);
        }
        return false;
    }

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(4);

        ArrayList<Edge> edgeList = new ArrayList<Edge>(4);
        edgeList.add(new Edge(0, 1));
        edgeList.add(new Edge(0, 3));
        edgeList.add(new Edge(2, 3));
        edgeList.add(new Edge(1, 2));
        
        System.out.println( "Is the graph cyclic? Answer : " + ds.isCyclic(edgeList) );
    }
}
