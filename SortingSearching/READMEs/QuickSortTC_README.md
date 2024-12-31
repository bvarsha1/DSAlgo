# Understanding Amortization and QuickSort Complexity

## What Does Amortization Mean?

Amortization in the context of algorithms refers to analyzing the **average time per operation** over a sequence of operations, even if some individual operations are more costly than others. It smooths out the costs of infrequent, expensive operations by averaging them across the entire sequence.

### Key Points:
- Amortized analysis considers the **overall cost** of a sequence of operations.
- It ensures that, on average, each operation runs efficiently, even if some individual operations are expensive.

### Example:
For a **dynamic array** that doubles its size when full:
- Inserting an element usually takes \( O(1) \).
- Occasionally, resizing the array costs \( O(n) \).
- However, over \( n \) insertions, the average time per operation is still \( O(1) \), because resizing happens infrequently and its cost is spread over multiple insertions.

---

## Explaining \( O(n + n/2 + n/4 + \dots) \):

This summation appears when analyzing algorithms like QuickSelect or QuickSort in scenarios where the size of the array is reduced by half at each step. Let’s break it down:

### 1. **Work Per Recursive Call:**
- At each level of recursion, the algorithm processes a subarray.
- If the size of the subarray is halved at each step, the work done at each level is proportional to \( n, n/2, n/4, \dots \).

### 2. **Total Work Summation:**
- Adding up the work done at each level:
  \
 \[
  T(n) = n + \frac{n}{2} + \frac{n}{4} + \frac{n}{8} + \dots
  \]

### 3. **Convergence to \( O(n) \):**
- This is a geometric series with a ratio of \( \frac{1}{2} \). The sum of an infinite geometric series is given by:
  \
 \[
  S = \frac{a}{1 - r}
  \]
  where \( a \) is the first term and \( r \) is the common ratio.

- For this series:
  \
 \[
  S = \frac{n}{1 - \frac{1}{2}} = 2n
  \]

- The total work is proportional to \( 2n \), which simplifies to \( O(n) \).

### 4. **Intuition:**
- Each level of recursion processes less data because the array size decreases exponentially.
- While the first few levels involve processing most of the data, later levels contribute progressively smaller amounts of work.
- The cumulative work is dominated by the first few levels, which sum up to \( O(n) \).

---

### Key Takeaway:
- **Amortization** helps us analyze algorithms where the cost of certain operations is unevenly distributed.
- In the case of \( O(n + n/2 + n/4 + \dots) \), the geometric series converges to \( O(n) \), reflecting the total work for recursive algorithms like QuickSelect and optimized QuickSort.

