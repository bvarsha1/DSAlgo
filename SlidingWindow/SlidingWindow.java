
import java.util.HashMap;
import java.util.HashSet;

public class SlidingWindow {

    public void housing(int[] plots, int n, int targetSum) {
        int i = 0, j = 0;
        int currentSum = 0;

        while(j < n) {

            // add the current jth plot area
            // keep expanding
            currentSum += plots[j];
            j++;

            // contraction condition
            while(currentSum > targetSum && i < j) {
                // subtract the removed element
                currentSum -= plots[i];
                //move forward
                i++;
            }

            if(currentSum == targetSum) {
                System.out.println(i + " - " + (j-1));
            }
        }
    }

    public String uniqueSubstring(String str) {
        // to store characters and their last occurence within the ongoing window
        HashMap<Character, Integer> charMap = new HashMap<>();

        // need a variable to store largest window size
        int largestWindow = 0;
        // to be able to return the string for largest window starting from this index
        int winStart = -1;
        int i = 0, j = 0;
        int currWinLen = 0;

        while(j < str.length()) {
            // get current character
            char ch = str.charAt(j);

            // expansion - happens if the character is not already in the window otherwise contract
            
            // if inside hashmap then, contract by last occurrence + 1; 
            if(charMap.containsKey(ch) && charMap.get(ch) >= i) {
                i = charMap.get(ch) + 1;
                currWinLen = j - i;
            }

            // else include and add 1 to winLen
            charMap.put(ch, j);
            currWinLen++;
            j++;

            if(currWinLen > largestWindow) {
                largestWindow = currWinLen;
                winStart = i;
            }
        }
        return str.substring(winStart, winStart + largestWindow);
    }

    public String stringWindow(String s, String p) {

        // s1 - string; s2 - pattern
        HashMap<Character, Integer> patFreq = new HashMap<>();
        HashMap<Character, Integer> strFreq = new HashMap<>();

        for(char ch : p.toCharArray()) {
            patFreq.put(ch, patFreq.getOrDefault(ch, 0) + 1);
        }

        // sliding window approach
        int count = 0;
        int start = 0; // left contraction
        int minWinSize = Integer.MAX_VALUE;
        int bestStart = -1;

        for(int i = 0; i < s.length(); i++) {
            // expand the window towards right unless the pattern fits in the string
            // include the character
            char ch = s.charAt(i);
            strFreq.put(ch, strFreq.getOrDefault(ch, 0) + 1);

            // count how many characters have been matched till now
            // we determine if the character is useful after being added, only if useful we increment count
            if( patFreq.containsKey(ch) && patFreq.get(ch) >= strFreq.get(ch) ) {
                count++;
            }
            // keep track if all characters of pattern have been matched
            // then start contracting
            if(count == p.length()) {
                // checking if the character at start index is not in pattern or has greater freq than freq in pattern
                // then remove it
                char startCh = s.charAt(start);
                while( !patFreq.containsKey(startCh) || strFreq.get(startCh) > patFreq.get(startCh))  {
                    strFreq.put(startCh, strFreq.get(startCh) - 1);
                    start++;
                    startCh = s.charAt(start);
                }

                // note window size
                int winSize = i - start + 1;
                if(winSize < minWinSize) {
                    minWinSize = winSize;
                    bestStart = start;
                }
            }

        }

        if(bestStart == -1) {
            return "No window found";
        }
        return s.substring(bestStart, bestStart + minWinSize);
    }

    public String smallestUniqueWindow(String str) {
        // put all the distinct characters in a set
        HashSet<Character> charExists = new HashSet<>();
        for(char ch : str.toCharArray()) {
            if(!charExists.contains(ch))
                charExists.add(ch);
        }

        // maintain window frequency map
        HashMap<Character, Integer> freq = new HashMap<>();

        int count = 0; // to maintain count of distinct chars matched
        int start = 0;
        int minWinSize = Integer.MAX_VALUE;
        int minStart = -1;

        for(int i = 0; i < str.length(); i++) {
            // expand unless all the chars come
            char ch = str.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);

            if(charExists.contains(ch) && freq.get(ch) <= 1)
                count++;
            
            if(count == charExists.size()) {
                // contraction from start index
                while( !charExists.contains(str.charAt(start)) || freq.get(str.charAt(start)) > 1 ) {
                    freq.put(
                        str.charAt(start),
                        freq.get(str.charAt(start)) - 1
                    );
                    start++;
                }

                // count will still be equal
                // track window
                int winSize = i - start  + 1;
                if(winSize < minWinSize) {
                    minWinSize = winSize;
                    minStart = start;
                }
            }
        }

        return str.substring(minStart, minStart + minWinSize);
    }

    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();

        // Problem 1 : Housing

        int[] plots = {1, 3, 2, 1, 4, 1, 3, 2, 1, 1};
        int n = plots.length;
        int k = 8;

        System.out.println("Available plots are: ");
        sw.housing(plots, n, k);

        // Problem 1.1 : Housing follow up - smallest window, handling negative integers in the array

        // Problem 2 : Largest Unique Substring

        String input = "prateekbhaiya";
        System.out.println("Largest unique substring: " + sw.uniqueSubstring(input));

        // Problem 3 : 2 String window - all characters of smaller string in "smallest window" in bigger string

        String a = "helloworld";
        String b = "lol";

        System.out.println("Smallest substring consisting all characters of smaller string: " + sw.stringWindow(a, b));

        // Problem 4 : Smallest Distinct Window

        //String str = "aabcbcdbcaaad";
        String str = "aaaa";
        System.out.println("Smallest distinct substring: " + sw.smallestUniqueWindow(str));

        // Problem 5 : Sliding Window Max
    }
}