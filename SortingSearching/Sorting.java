package SortingSearching;

import java.util.Arrays;

public class Sorting {
    
    public void merge(int[] arr, int s, int e) {
        int i = s;
        int m = (s + e)/2;
        int j = m + 1;

        int[] temp = new int[e - s + 1];
        int k = 0;
        while(i <= m && j <= e) {
            if(arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while(i <= m) {
            temp[k++] = arr[i++];
        }

        while(j <= e) {
            temp[k++] = arr[j++];
        }

        int idx2 = 0;
        for(int idx = s; idx <= e; idx++) {
            arr[idx] = temp[idx2++];
        }
    }

    public void mergeSort(int[] arr, int s, int e) {
        // base case
        if(s >= e) {
            return;
        }

        // rec case
        int mid = (s + e)/2;
        mergeSort(arr, s, mid);
        mergeSort(arr, mid + 1, e);
        merge(arr, s, e);
    }

    // returns total inversions
    public int invertionsCount(int[] arr, int s, int e) {
        // base case
        if(s >= e)
            return 0;

        // rec case
        int mid = (s + e) / 2;
        int left = invertionsCount(arr, s, mid);
        int right = invertionsCount(arr, mid + 1, e);

        return left + right + inverseMerge(arr, s, e);
    }
    // returns number of cross inverstions
    public int inverseMerge(int[] arr, int s, int e) {
        int i = s;
        int m = ( s + e ) / 2;
        int j = m + 1;

        int[] tmp = new int[e - s + 1];
        int k = 0;
        int inversCnt = 0;
        while(i <= m && j <= e) {
            if(arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
                inversCnt += (m - i + 1);
            }
        }

        while(i <= m) {
            tmp[k++] = arr[i++];
        }

        while(j <= e) {
            tmp[k++] = arr[j++];
        }

        int idx2 = 0;
        for(int idx = s; idx <= e; idx++) {
            arr[idx] = tmp[idx2++];
        }

        return inversCnt;
    }

    public void quickSort(int[] arr, int s, int e) {
        // base case
        if(s >= e)
            return;

        // recursive case
        // partitions the array and returns the true position of pivot
        int p = partition(arr, s, e);

        // quicksort on the left
        quickSort(arr, s, p - 1);
        // quicksort on the right
        quickSort(arr, p + 1, e);
    }

    public int partition(int[] arr, int s, int pivot) {
        int i = s - 1;
        for(int j = s; j < pivot; j++) {
            if(arr[j] < arr[pivot]) {
                i++;
                // swap
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[pivot];
        arr[pivot] = temp;
        return i+1;
    }

    public int quickSelect(int[] arr, int s, int e, int k) {
        // base case
        if(s >= e)
            return -1;

        // recursive case
        int p = partition(arr, s, e);

        if(p < k) {
            return quickSelect(arr, p + 1, e, k);
        } else if(p > k) {
            return quickSelect(arr, s, p - 1, k);
        } else {
            return arr[p];
        }
    }

    public String smallestString(String[] strs) {
        // divide and conquer
        Arrays.sort(strs, (o1, o2) -> (o1+o2).compareTo(o2+o1));

        String result = new String();
        for(String s : strs) {
            result += s;
        }
        return result;
    }

    public static void main(String[] args) {

        Sorting sort = new Sorting();

        // Problem 1 : Merge Sort

        int[] arr = new int[] {10, 5, 2, 0, 7, 6, 4};

        int s = 0;
        int e = arr.length - 1;
        sort.mergeSort(arr, s, e);
        System.out.println("Results of Merge Sort : ");
        for(int x : arr)
            System.out.print(x + " ");
        System.out.println();

        // Problem 2 : Inversion Count
        
        int[] arr2 = new int[] {0, 5, 2, 3, 1};

        int start = 0;
        int end = arr2.length - 1;
        
        System.out.println("Total inversions : " + sort.invertionsCount(arr2, start, end));

        // Problem 3 : Quick Sort
        // Understand the avg/worst case complexity of the algo

        int[] arr3 = new int[] {10, 5, 2, 0, 7, 6, 4};
        sort.quickSort(arr3, 0, arr3.length - 1);
        System.out.println("Results of Quick Sort : ");
        for(int x : arr3)
            System.out.print(x + " ");
        System.out.println();

        // Problem 4 : Quick Select - Kth smallest element
        // Q. What is the the avg/worst case complexity of the algo?

        int[] arr4 = new int[] {10, 5, 2, 0, 7, 6, 4};
        int k = 4;
        int kthSmallest = sort.quickSelect(arr4, 0, arr4.length - 1, k);
        System.out.println("The Kth smallest element is : " + kthSmallest);

        // Problem 5 : Smallest String

        String[] strs = new String[] {"a", "ab", "aba"};
        System.out.println("Lexicographically smallest string formed : " + sort.smallestString(strs));
    }
}
