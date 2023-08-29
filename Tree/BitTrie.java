package Tree;

public class BitTrie {
    private class Node {
        Node zero;
        Node one;
    }

    Node root;
    public BitTrie(){
        root = new Node();
    }

    public void createBitTrie(int[] nums) {
        for(int x : nums) {
            insertNum(x);
        }
    }

    public void insertNum(int n) {
        Node temp = root;
        for(int i = 31; i >= 0; i--) {
            int bit = (n>>i)&1; // bitmask
            if(bit == 0) {
                // check if zero is not null
                if(temp.zero == null) {
                    temp.zero = new Node();
                }
                temp = temp.zero;
            } else {
                if(temp.one == null) {
                    temp.one = new Node();
                }
                temp = temp.one;
            }
        }
    }

    public int maxXORHelper(int n) {
        int curr = 0;
        Node temp = root;
        for(int i = 31; i >= 0; i--) {
            int bit = (n>>i)&1;
            if(bit == 0) {
                // look for a one
                if(temp.one != null) {
                    // go to this node and add contribution
                    temp = temp.one;
                    curr += (1 << i);
                } else {
                    temp = temp.zero;
                }
            } else {
                // look for a zero
                if(temp.zero != null) {
                    temp = temp.zero;
                    curr += (1 << i);
                } else {
                    temp = temp.one;
                }
            }
        }

        return curr;
    }

    public int maxXOR(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            insertNum(num);
            max = Math.max(max, maxXORHelper(num));
        }
        return max;
    }

    public static void main(String[] args) {
        // Problem 4 : Biggest XOR (Trie + Bitmask)
        BitTrie t = new BitTrie();
        int[] numbers = new int[] {3, 10, 5, 25, 9, 2};
        // t.createBitTrie(numbers);
        // t.insertNum(10);
        System.out.println("The max XOR with any 2 given numbers in the array : " + t.maxXOR(numbers));
    }
}
