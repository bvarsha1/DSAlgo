## Heap Sort

### 1. What is Heap Sort?

**Heap Sort** is a comparison-based sorting algorithm that uses a **Binary Heap** data structure.

<br>

### 2. What is a Binary Heap?

A **Binary Heap** is a binary tree with two rules:

#### **(A) Shape Property**

* It must be a **complete binary tree**
* Meaning: filled level by level from left to right

#### **(B) Heap Property**

Two types exist:

| Heap Type    | Property          |
| ------------ | ----------------- |
| **Max-Heap** | Parent ≥ Children |
| **Min-Heap** | Parent ≤ Children |

Heap sort uses a **Max-Heap** for **ascending** sorting.

<br>

### 3. Idea Behind Heap Sort

To sort in ascending order:

1. **Build a Max-Heap** from input array
2. The **largest element** is at index 0
3. Swap it with the **last element**
4. Reduce heap size by 1
5. Heapify the root again
6. Repeat until size = 1

<br>

### 4. Why Max-Heap?

Because:

* Root contains **maximum**
* We push max to the end
* So array becomes sorted from end → beginning

Example:

Input: `[4, 10, 3, 5, 1]`
Max elements pulled in order: `10 → 5 → 4 → 3 → 1`

Sorted result: `[1, 3, 4, 5, 10]`

<br>

### 5. Time & Space Complexity

| Phase                 | Time           |
| --------------------- | -------------- |
| Build Max-Heap        | `O(n)`         |
| Extract max `n` times | `O(n log n)`   |
| **Total**             | **O(n log n)** |

**Space Complexity:** `O(1)` → **In-place sorting**

<br>

### 6. Steps in Detail

#### **Step 1: Build Max-Heap**

Start from first non-leaf node:
```
i = n/2 - 1  down to 0
```

Reason:
* Nodes after `n/2 - 1` are leaves (already heaps)


#### **Step 2: Extract Max Repeatedly**

For `i = n-1` down to `1`:

1. Swap `arr[0]` and `arr[i]`
2. Heapify root (`index 0`)
3. Now heap size = `i`

<br>

### 7. Heap Sort Program (Java)

```java
class HeapSort {

    public void sort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract elements
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Heapify reduced heap
            heapify(arr, i, 0);
        }
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i;           // Initialize largest as root
        int left = 2 * i + 1;      // left child
        int right = 2 * i + 2;     // right child

        // If left child is larger
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If root is not largest
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }
    }

    // Driver code
    public static void main(String[] args) {
        HeapSort hs = new HeapSort();
        int[] arr = { 4, 10, 3, 5, 1 };
        hs.sort(arr);

        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
```

<br>

### 8. Why Heap Sort is Good

✔ Works in-place
✔ Guaranteed `O(n log n)` worst-case
✔ No extra memory like MergeSort
✔ Not vulnerable to bad pivot like QuickSort

<br>

### 9. Why Heap Sort is Not Always Used

- ❌ Cache-unfriendly memory access (tree structure)
- ❌ Slower constants than QuickSort in real-world
