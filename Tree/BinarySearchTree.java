package Tree;

import java.util.ArrayDeque;
import java.util.Arrays;

public class BinarySearchTree {
    private class Node {
        int key;
        Node left, right;

        private Node(int key) {
            this.key = key;
            left = right = null;
        }
    }

    Node root = null;

    public BinarySearchTree(int[] arr, boolean balanced) {
        if(balanced) {
            Arrays.sort(arr);
            root = buildMinBST(arr, 0, arr.length - 1);
        } else {
            for(int x : arr) {
                root = insertNode(root, x);
            }
        }
    }

    public Node buildMinBST(int[] arr, int start, int end) {
        // base case
        if(start > end) {
            return null;
        }

        // recursive case
        int mid = (start + end) / 2;
        Node root = new Node(arr[mid]);

        root.left = buildMinBST(arr, start, mid - 1);
        root.right = buildMinBST(arr, mid + 1, end);

        return root;
    }

    public Node insertNode(Node root, int key) {
        // base case
        if(root == null) {
            return new Node(key);
        }

        // recursive case
        if(key <= root.key) {
            root.left = insertNode(root.left, key);
        } else {
            root.right = insertNode(root.right, key);
        }

        return root;
    }

    public void printInorder(Node root) {
        if(root == null)
            return;

        printInorder(root.left);
        System.out.print(root.key + " ");
        printInorder(root.right);
    }

    public boolean search(Node root, int key) {
        // base case
        if(root == null)
            return false;

        if(root.key == key)
            return true;
        
        if(key <= root.key) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }
    }

    public int findClosest(int key) {

        int closest = -1;
        int diff = Integer.MAX_VALUE;

        Node node = root;
        while(node != null) {
            int currDiff = Math.abs(key - node.key);

            // if we find the target key
            if(currDiff == 0)
                return node.key;

            if(currDiff < diff) {
                diff = currDiff;
                closest = node.key;
            }

            // traverse right or left
            if(key < node.key)
                node = node.left;
            else
                node = node.right;
        }

        return closest;
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
                System.out.print(curr.key + " ");
                if(curr.left != null)
                    q.offer(curr.left);
                if(curr.right != null)
                    q.offer(curr.right);
            }
        }
    }

    // BST to sorted Linked List

    private class LinkedList {
        Node head;
        Node tail;

        public void printLL() {
            Node temp = head;
            while(temp != null) {
                System.out.print(temp.key + " ");
                temp = temp.right;
            }
            System.out.println();
        }
    }

    LinkedList BST2List(Node root) {
        LinkedList list = new LinkedList();
        // base case
        if(root == null) {
            list.head = list.tail = null;
            return list;
        }

        // check the 4 cases
        // case  1 - leaf node
        if(root.left == null && root.right == null) {
            list.head = list.tail = root;
        } else if(root.left != null && root.right == null) {
            LinkedList leftLL = BST2List(root.left);
            // attach the leftLL to root
            leftLL.tail.right = root;

            list.head = leftLL.head;
            list.tail = root;
        } else if(root.left == null && root.right != null) {
            LinkedList rightLL = BST2List(root.right);
            // attach the rightLL to root
            root.right = rightLL.head;

            list.head = root;
            list.tail = rightLL.tail;
        } else if(root.left != null && root.right != null) {
            LinkedList leftLL = BST2List(root.left);
            LinkedList rightLL = BST2List(root.right);

            // attach the leftLL and rightLL to root
            leftLL.tail.right = root;
            root.right = rightLL.head;

            list.head = leftLL.head;
            list.tail = rightLL.tail;
        }
        return list;
    }

    public Node inorderSuccessor(Node target) {
        // Case 1 : there exists a right subtree
        if(target.right != null) {
            // move to leftmost - the lowest element
            Node temp = target.right;
            while(temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        // Case 2 : there exists no right subtree
        // store smallest successor in a variable
        Node temp = root;
        Node succ = null;

        while(temp!= null) {
            if(temp.key > target.key) {
                succ = temp;
                // move left as we want the smallest value greater than target
                temp = temp.left;
            } else if(temp.key < target.key) {
                temp = temp.right;
            } else {
                // reached the target
                break;
            }
        }
        return succ;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {8, 3, 10, 1, 6, 14, 4, 7, 13};
        BinarySearchTree bst = new BinarySearchTree(arr, false);

        System.out.println("The root of BST is : " + bst.root.key);
        System.out.println("The inorder traversal of tree is : ");
        bst.printInorder(bst.root);
        System.out.println();

        // Problem 1 : Search if key is present
        System.out.println("Is 6 present in the BST? Answer : " + bst.search(bst.root, 6));
        System.out.println("Is 12 present in the BST? Answer : " + bst.search(bst.root, 12));

        // Problem 2 : Minimum height BST from sorted array
        BinarySearchTree minBST = new BinarySearchTree(arr, true);
        System.out.println("The root of minimum BST is : " + minBST.root.key);
        System.out.println("The inorder traversal of the min BST is : ");
        minBST.printInorder(minBST.root);
        System.out.println();

        // Problem 3 : Closest in BST
        // Recursive ( O(H) space complexity ) / Iterative ( O(1) space complexity )
        int key = 16;
        System.out.printf("The closest to number %d in the BST is : %d\n", key, bst.findClosest(key));

        // Problem 4 : Flatten the BST / Convert to sorted list
        System.out.println("The flattened linked list is : ");
        LinkedList ll = minBST.BST2List(minBST.root);
        ll.printLL();
        
        // Problem 5 : Inorder successort in BST
        Node target = bst.root.left;
        System.out.println("The inorder successor of " + target.key +" is : " + bst.inorderSuccessor(target).key);

        Node target2 = bst.root.right.right.left;
        System.out.println("The inorder successor of " + target2.key +" is : " + bst.inorderSuccessor(target2).key);

        bst.printLevelOrderNewLine();

        // Problem 6 : IsBST

        // Problem 7 : Special BST

        // Problem 8 : LCA

        // Problem 9 : Shortest Tree Path
    }
}
