package Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.TreeMap;

public class BinaryTree {
    private class Node {
        int data;
        Node left, right;

        private Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    Node root = null;

    private class DParams {
        int height;
        int diameter;

        private DParams(int h, int d) {
            height = h;
            diameter = d;
        }
    }

    private class Balanced {
        int height;
        boolean isBalanced = false;

        private Balanced(int h, boolean isBal) {
            height = h;
            isBalanced = isBal;
        }
    }

    // build tree in level-order 
    public void buildTree(ArrayList<Integer> list) {
        if(list.size() == 0) {
            return;
        }

        ArrayDeque<Node> q = new ArrayDeque<>();
        root = new Node( list.remove(0) );
        q.offer( root );

        while(!q.isEmpty()) {
            Node curr = q.poll();

            int left = list.remove(0);
            int right = list.remove(0);

            if(left != -1) {
                curr.left = new Node(left);
                q.offer(curr.left);
            }

            if(right != -1) {
                curr.right = new Node(right);
                q.offer(curr.right);
            }
        }
    }

    public void buildTreeWrapper(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int x : arr) {
            list.add(x);
        }

        buildTree(list);
    }


    public void printTreeLevelOrder() {
        if(root == null)
            return;

        ArrayDeque<Node> q = new ArrayDeque<>();
        q.offer(root);

        System.out.print("The tree level order is : ");
        while(!q.isEmpty()) {
            Node curr = q.poll();
            System.out.print(curr.data + " ");
            if(curr.left != null)
                q.offer(curr.left);
            if(curr.right != null)
                q.offer(curr.right);
        }
        System.out.println();
    }

    public void printLevelOrderNewLine() {
        if(root == null)
            return;

        ArrayDeque<Node> q = new ArrayDeque<>();
        // as ArrayDeque wouldn't take null values, creating a levelEnd identifier
        Node levelEnd = new Node(Integer.MAX_VALUE);
        q.offer(root);
        q.offer(levelEnd);

        System.out.println("The tree level order is : ");
        while(!q.isEmpty()) {
            if(q.peek() == levelEnd) {
                // remove null
                q.poll();
                // print new line
                System.out.println();
                // if q is not empty after null, insert null
                if(!q.isEmpty())
                    q.offer(levelEnd);
            }
            else {
                Node curr = q.poll();
                System.out.print(curr.data + " ");
                if(curr.left != null)
                    q.offer(curr.left);
                if(curr.right != null)
                    q.offer(curr.right);
            }
        }
    }

    public int height(Node node) {
        if(node == null)
            return 0;

        int height1 = height(node.left);
        int height2 = height(node.right);

        return 1 + Math.max(height1, height2);
    }

    public int diameter(Node node) {
        // base case
        if(node == null) {
            return 0;
        }

        // recursive case
        int D1 = height(node.left) + height(node.right);
        int D2 = diameter(node.left);
        int D3 = diameter(node.right);

        return Math.max(D1, Math.max(D2, D3));
    }

    public DParams diameterOpt(Node node) {
        if(node == null) {
            return new DParams(0, 0);
        }

        DParams left = diameterOpt(node.left);
        DParams right = diameterOpt(node.right);

        int D1 = left.height + right.height;
        int D2 = left.diameter;
        int D3 = right.diameter;

        return new DParams(
            1+ Math.max(left.height, right.height),
            Math.max(D1, Math.max(D2, D3))
        );
    }

    public int descendantSumTree(Node root) {
        // base case
        if(root == null)
            return 0;

        // we need to consider the case of leaf nodes in a spacial way
        // they would be updated with their own value
        if(root.left == null && root.right == null) {
            return root.data;
        }
        
        int leftSum = descendantSumTree(root.left);
        int rightSum = descendantSumTree(root.right);
        int curr = root.data;

        root.data = leftSum + rightSum;

        return curr + root.data;
    }

    public Balanced isBalancedTree(Node root) {
        if(root == null) {
            return new Balanced(0, true);
        }

        Balanced left = isBalancedTree(root.left);
        Balanced right = isBalancedTree(root.right);

        int height = 1 + Math.max(left.height, right.height);
        boolean isBalanced = (
                left.isBalanced &&
                right.isBalanced &&
                Math.abs(left.height - right.height) <= 1
            ) ? true : false;

        return new Balanced(height, isBalanced);
    }

    private class Inclusion {
        int inc = 0, exc = 0;

        private Inclusion(int i, int e) {
            inc = i;
            exc = e;
        }
    }

    public Inclusion maxSubsetSum(Node root) {
        // base case
        if(root == null)
            return new Inclusion(0, 0);

        Inclusion left = maxSubsetSum(root.left);
        Inclusion right = maxSubsetSum(root.right);

        // calculate sum when including root data
        int include = root.data + left.exc + right.exc;
        // maximise sum when root not included
        int exclude = Math.max(left.inc, left.exc) + Math.max(right.exc, right.inc);

        return new Inclusion(include, exclude);
    }

    public void levelK(Node root, int k, ArrayList<Integer> result) {
        // base case
        if(root == null)
            return;
        if(k == 0) {
            result.add(root.data);
            return;
        }

        // recursive case
        levelK(root.left, k - 1, result);
        levelK(root.right, k - 1, result);
    }

    public int nodesAtDistanceK(Node root, int k, int target, ArrayList<Integer> result) {
        if(root == null)
            return -1;

        if(root.data == target) {
            levelK(root, k, result);
            return 0; // since if we are at target, the distance will be 0
        }

        // at this point we still haven't found the target or know if its in left or right
        int DL = nodesAtDistanceK(root.left, k, target, result);
        if(DL != -1) { // if not -1, it was found in left
            // add the current node if d + 1 = k
            if(DL + 1 == k) {
                result.add(root.data);
            }
            // print in right
            else {
                levelK(root.right, k - DL - 2, result);
            }
            return 1 + DL;
        }

        int DR = nodesAtDistanceK(root.right, k, target, result);
        if(DR != -1) { // if not -1, the node was found in right
            // add the current node if d + 1 = k
            if(DR + 1 == k) {
                result.add(root.data);
            }
            // print in left
            else {
                levelK(root.left, k - DR - 2, result);
            }
            return 1 + DR;
        }

        return -1;
    }

    public void verticalOrder(Node root, int k, TreeMap<Integer, ArrayList<Integer>> result) {
        if(root == null)
            return;
        
        ArrayList<Integer> list = result.getOrDefault(k, new ArrayList<Integer>());
        list.add(root.data);
        result.put(k, list);

        verticalOrder(root.left, k - 1, result);
        verticalOrder(root.right, k + 1, result);
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        // Problem 1 : Build tree with level order traversal
        int[] arr = new int[] {1, 2, 3, 4, 5, -1, 6, -1, -1, 7, -1, -1, -1, -1, -1};
        bt.buildTreeWrapper(arr);
        bt.printTreeLevelOrder();
        bt.printLevelOrderNewLine();

        // Problem 2 : Tree diameter
        System.out.println("The diameter of the tree is : " + bt.diameter(bt.root));

        // Problem 2.1 : Tree diameter - optimized to O(N)
        System.out.println("The diameter of the tree (optimised approach) is : " + bt.diameterOpt(bt.root).diameter);

        // Problem 3 : Replace with descendant sum
        System.out.println("The tree replaced with its descendant sum is : ");
        bt.descendantSumTree(bt.root);
        bt.printLevelOrderNewLine();

        // Problem 4 : Height of the tree
        System.out.println("The height of the tree is : " + bt.height(bt.root));

        // Problem 5 : Height balanced trees
        System.out.println("Is the tree balanced? Answer : " + bt.isBalancedTree(bt.root).isBalanced);

        BinaryTree bt2 = new BinaryTree();
        int[] arr2 = new int[] {1, 2, 3, 4, 5, -1, 6, -1, -1, 7, -1, -1, -1, 8, -1, -1, -1};
        bt2.buildTreeWrapper(arr2);
        bt2.printLevelOrderNewLine();
        System.out.println("Is the tree balanced? Answer : " + bt2.isBalancedTree(bt2.root).isBalanced);

        // Problem 6 : Max subset sum
        Inclusion maxSum = bt2.maxSubsetSum(bt2.root);
        System.out.println("The maximum subset sum of the tree : " + Math.max(maxSum.inc, maxSum.exc));

        // Problem 7 : Print nodes at level K
        ArrayList<Integer> nodesAtK = new ArrayList<>();
        bt2.levelK(bt2.root, 2, nodesAtK);
        System.out.println("Nodes at level k : ");
        for(int x : nodesAtK) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Problem 8 : Nodes at distance K
        ArrayList<Integer> result = new ArrayList<>();
        bt2.nodesAtDistanceK(bt2.root, 2, 5, result);
        System.out.println("Nodes at distance k : ");
        for(int x : result) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Problem 9 : Vertical order print
        // treemaps are sorted maps
        TreeMap<Integer, ArrayList<Integer>> sortedMap = new TreeMap<>();
        bt2.verticalOrder(bt2.root, 0, sortedMap);
        System.out.println("Vertical Order of tree is : ");
        for(int x : sortedMap.keySet()) {
            for(int y : sortedMap.get(x)) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        // Problem 10 : Left View

        // Problem 11 : Sibling swap
    }
}