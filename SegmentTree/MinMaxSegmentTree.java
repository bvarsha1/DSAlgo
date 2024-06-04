package SegmentTree;

public class MinMaxSegmentTree {
    int[] tree;
    int size;

    public MinMaxSegmentTree(int[] arr) {
        size = arr.length;
        tree = new int[4*size];
        buildTree(arr, 0, 0, size - 1);
    }

    private void buildTree(int[] arr, int tIdx, int left, int right) {
        // leaf node
        if(left == right) {
            tree[tIdx] = arr[left];
            return;
        }

        int mid = (left + right) / 2;
        int leftChild = 2*tIdx + 1;
        int rightChild = 2*tIdx + 2;
        buildTree(arr, leftChild, left, mid);
        buildTree(arr, rightChild, mid + 1, right);
        tree[tIdx] =  Math.min(tree[leftChild], tree[rightChild]);
    }

    public int getMin(int stLeft, int stRight, int qLeft, int qRight, int currIdx) {
        // complete overlap
        if(qLeft <= stLeft && qRight >= stRight) return tree[currIdx];

        // no overlap
        if(qLeft > stRight || qRight < stLeft) return Integer.MAX_VALUE;

        int mid = (stLeft + stRight) / 2;

        // partial overlap
        return Math.min( getMin(stLeft, mid, qLeft, qRight, 2*currIdx + 1),
            getMin(mid + 1, stRight, qLeft, qRight, 2*currIdx + 2));
    }

    public void updateValue(int arr[], int i, int newVal) {
        if(i < 0 || i > size - 1) return;

        int diff = newVal - arr[i];
        arr[i] = newVal;

        update(0, 0, size - 1, i, diff);
    }

    private void update(int currIdx, int stLeft, int stRight, int pos, int diff) {
        // no overlap
        if(pos > stRight || pos < stLeft) return;

        tree[currIdx] += diff;

        if(stLeft != stRight) {
            int mid = (stLeft + stRight) / 2;
            update(2*currIdx + 1, stLeft, mid, pos, diff);
            update(2*currIdx + 2, mid + 1, stRight, pos, diff);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 5, 6, 7, 9};
        MinMaxSegmentTree st = new MinMaxSegmentTree(arr);

        int rangeMin = st.getMin(0, 5, 2, 4, 0);
        System.out.println("The min in give range: " + rangeMin);

        //st.updateValue(arr, 3, 14);
        System.out.println("The min in entire array: " + st.tree[0]);

        // int rangeSumUpdated = st.getSum(0, 5, 2, 4, 0);
        // System.out.println("The sum in give range: " + rangeSumUpdated);
    }
}