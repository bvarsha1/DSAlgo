package SortingSearching;

public class Searching {

    // Modified Binary Search
    // Improve the average case complexity, may not improve worst case
    public int sparseSearch(String[] strs, String key) {
        int s = 0, e = strs.length - 1;

        // binary search
        while(s <= e) {
            // calculate mid point
            int mid = (s + e) / 2;
            System.out.print("Start = " + s + "; End = " + e);
            int mid_left = mid - 1;
            int mid_right = mid + 1;
            int comparision = strs[mid].compareTo(key);
            System.out.print("Compare = " + comparision);
            if(strs[mid].equals("")) {
                // update mid to point to a nearest non-empty string
                while(true) {
                    if(mid_left < s && mid_right > e) {
                        return -1;
                    } else if(mid_right <= e && !strs[mid_right].equals("")) {
                        mid = mid_right;
                        break;
                    } else if(mid_left>= s && !strs[mid_left].equals("")) {
                        mid = mid_left;
                        break;
                    }
                    mid_left--;
                    mid_right++;
                }
            } else if(comparision == 0) {
                return mid;
            } else if(comparision > 0) {
                System.out.println("mid is larger than key");
                e = mid - 1;
            } else if(comparision < 0) {
                System.out.println("mid is smaller than key");
                s = mid + 1;
            }

            //System.out.print("car".compareTo(key));

        }

        return -1;
    }

    public static void main(String[] args) {
        Searching search = new Searching();

        // Problem 1 : Sparse search
        String[] strs = new String[] {
            "ai", "", "", "bat", "", "", "car", "cat", "", "", "dog", ""
        };

        String key = "bat";

        System.out.println("Index of the input string : " + search.sparseSearch(strs, key));
    }
}
