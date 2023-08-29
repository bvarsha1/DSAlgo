package Tree;

import java.util.HashMap;
import java.util.HashSet;

public class Trie {

    public class Node {
        public char ch;
        public HashMap<Character, Node> map;
        public boolean isEnd = false;

        public Node(char ch) {
            this.ch = ch;
            map = new HashMap<>();
            this.isEnd = false;
        }
    }

    public Node root;

    public Trie() {
        root = new Node('\0');
    }

    public void createTrie(String[] words) {
        for(String word : words) {
            insertWord(word);
        }
    }

    public void insertWord(String word) {
        Node temp = root;
        // iterate over all characters of the word
        for(char ch : word.toCharArray()) {
            if(!temp.map.containsKey(ch)) {
                Node node = new Node(ch);
                temp.map.put(ch, node);
            }
            temp = temp.map.get(ch);
        }
        temp.isEnd = true;
    }

    public boolean search(String word) {
        Node temp = root;
        // iterate over all characters
        for(char ch : word.toCharArray()) {
            if(!temp.map.containsKey(ch)) {
                return false;
            }
            temp = temp.map.get(ch);
        }
        // check if its the end
        return temp.isEnd;
    }

    public void documentSearch(String document, int i, HashSet<String> keyExists) {
        Node temp = root;
        for(int j = i; j < document.length(); j++) {
            char ch = document.charAt(j);

            // if at any point the character is not existing in trie, return
            if(!temp.map.containsKey(ch))
                return;
            // move forward
            temp = temp.map.get(ch);
            // check if end
            if(temp.isEnd) {
                String output = document.substring(i, j + 1);
                keyExists.add(output);
            }
        }
    }

    public static void main(String[] args) {
        String[] words = new String[] {"apple", "ape", "mango", "news", "no", "not", "never"};
        Trie t = new Trie();
        t.createTrie(words);

        // Problem 1 : Build and search a trie
        String searchKey = "apple";
        System.out.printf("Is the search key '%s' present? Answer : %s\n", searchKey, t.search(searchKey));

        // Problem 2 : Suffix Trie
        // Suffix trie take O(N^2) for insertion; O(M) for search where M is the length of search key
        // Check the video explanation, extension of prefix trie

        // Problem 3 : Document search - "cute little cat"
        String doc = "little cute cat loves to code in c++, java & python";
        String[] searchKeys = new String[] {"cute cat", "ttle", "cat", "quick", "big"};

        // create a trie of searchKeys
        Trie t2 = new Trie();
        t2.createTrie(searchKeys);

        HashSet<String> keyExists = new HashSet<>();
        for(int i = 0; i < doc.length(); i++) {
            t2.documentSearch(doc, i, keyExists);
        }

        // check if present in hash set
        System.out.println("The answer to document search are : ");
        for(String key : searchKeys) {
            System.out.print(key + " : " + keyExists.contains(key) + "; ");
        }

        // Problem 5 : Googly strings

        // Problem 6 : Phone number search
    }
}
