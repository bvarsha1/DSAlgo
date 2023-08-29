package LinkedList;

public class LinkedList {
    // create a node in the LinkedList
    private class Node {
        int data;
        Node next;

        private Node(int data) {
            this.data = data;
            next = null;
        }
    }

    Node head = null;

    public void insertAtHead(int data) {
        if(head == null) {
            head = new Node(data);
            return;
        }
        // otherwise
        Node n = new Node(data);
        n.next = head;
        head = n;
    }

    public void printList() {
        Node node = head;
        System.out.print("Resultant Linked List : ");
        while(node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    public void insertInMiddle(int data, int position) {
        if(position == 0) {
            insertAtHead(data);
            return;
        }

        Node node = head;
        for(int jump = 1; jump < position; jump++) {
            node = node.next;
        }

        Node temp = new Node(data);
        temp.next = node.next;
        node.next = temp;
    }

    public Node reverseRecursively(Node head) {
        // base case
        if(head == null || head.next == null)
            return head;

        // recursive case
        Node sHead = reverseRecursively(head.next);
        // last node of the returned list is head.next
        head.next.next = head;
        head.next = null;

        return sHead;
    }

    public Node reverseIteratively() {
        Node prev = null;
        Node curr = head;
        Node temp = null;

        while(curr != null) {
            temp = curr.next;
            curr.next = prev;
            // move forward
            prev = curr;
            curr = temp;
        }

        return prev;
    }

    public Node kReverse(Node head, int k) {
        // base case
        if(head == null)
            return null;

        // recursive case
        Node prev = null;
        Node curr = head;
        Node temp = null;
        // to iterate on k nodes exactly, we need a count
        int count = 1;

        while(curr!= null && count <= k) {
            // do same as iterative reverse
            temp = curr.next;
            // make curr's next point to prev
            curr.next = prev;
            // move forward
            prev = curr;
            curr = temp;
            // increment count to keep track of k nodes condition
            count++;
        }

        // temp is always 1 step ahead - so check if not null
        // it means its still not the end of the list, so we recursively join the smaller k-size lists
        if(temp != null) {
            // head comes to the end; so we point it to the node returned from recursive call
            head.next = kReverse(temp, k);
        }
        // prev is the old list's last  value - which when reversed becomes first value
        // so we return prev
        return prev;
    }

    public Node merge(Node a, Node b) {
        // base case
        if(a == null)
            return b;
        if(b == null)
            return a;

        // recursive call
        Node c = null;
        if(a.data < b.data) {
            c = a;
            c.next = merge(a.next, b);
        } else {
            c = b;
            c.next = merge(a, b.next);
        }

        return c;
    }

    public Node getMid(Node head) {
        if(head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head.next;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public int kthLast(int k) {
        if(head == null)
            return -1;

        Node fast = head;
        for(int i = 0; i < k; i++) {
            if(fast == null)
                return Integer.MIN_VALUE;
            fast = fast.next;
        }

        Node slow = head;
        while(fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow.data;
    }

    public Node mergeSort(Node head) {
        // base case
        if(head == null || head.next == null)
            return head;

        // recursive case
        Node a = head;
        Node mid = getMid(a);
        Node b = mid.next;
        mid.next = null;

        a = mergeSort(a);
        b = mergeSort(b);

        return merge(a, b);
    }
    public static void main(String[] args) {

        // Problem 1 : Insert at head of the list
        LinkedList ll = new LinkedList();

        ll.insertAtHead(0);
        ll.insertAtHead(1);
        ll.insertAtHead(2);
        ll.insertAtHead(3);
        ll.insertAtHead(4);
        ll.insertAtHead(5);

        ll.printList();

        // Problem 2 : Insert in the middle of the list
        ll.insertInMiddle(6, 0);
        ll.insertInMiddle(7, 3);

        ll.printList();

        // Problem 3 : Reverse a list recursively
        System.out.println("Reversed list (recursive) : ");
        ll.head = ll.reverseRecursively(ll.head);
        ll.printList();

        // Problem 4 : Reverse a list iteratively
        System.out.println("Reversed list (iterative) : ");
        ll.head = ll.reverseIteratively();
        ll.printList();

        // Problem 5 : k-reverse linkedList
        System.out.println("K-reversed list: ");
        ll.head = ll.kReverse(ll.head, 3);
        ll.printList();

        // Problem 6 : Merge 2 sorted lists
        System.out.println("Merged 2 sorted lists : ");

        LinkedList list1 = new LinkedList();
        list1.insertAtHead(6);
        list1.insertAtHead(3);
        list1.insertAtHead(2);

        LinkedList list2 = new LinkedList();
        list2.insertAtHead(10);
        list2.insertAtHead(7);
        list2.insertAtHead(5);
        list2.insertAtHead(1);

        LinkedList merged = new LinkedList();
        merged.head = ll.merge(list1.head, list2.head);

        merged.printList();

        // Problem 7 : Find middle element in the linked list
        System.out.println("The middle element is: " + ll.getMid(ll.head).data);

        // Problem 8 : Kth last element (runner)
        System.out.println("The Kth last element is: " + ll.kthLast(3));

        // Problem 9 : Merge sort
        System.out.println("Sorted list : ");
        ll.head = ll.mergeSort(ll.head);
        ll.printList();

        // Problem 10 : Detect a cycle (runner)
    }
}
