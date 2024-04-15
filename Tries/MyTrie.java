import java.util.HashMap;

/*
author: Leonardo de Farias

Below is an implementation of a Trie from scratch.
It supports the following fundamental operations:
---> insert: put in a word of length 'L' into the trie
---> search: find a word in the trie and return the position in the text
---> remove: delete a word in the trie
*/

interface TrieInterface {
    public void insert(String word);
    public int search(String word);
    public boolean remove(String word);
}

public class MyTrie implements TrieInterface {

    //convenient class for representing a trie node
    //where the markings for some node represents some
    //prefix. Additionally, it's easy to define L.
    public static class TrieNode {
        public boolean marked[]; //all the visited letters up to this pref
        public boolean isWord; //determining whether this pref already a complete word
        public TrieNode child[]; //all the children in respective locations
        public String pref;
        public TrieNode(int L, String P) {
            marked = new boolean[L];
            child = new TrieNode[L];
            for (int i=0; i<L; i++) {
                marked[i] = false;
                child[i] = null;
            }
            isWord = false;
            pref = P;
        }
        //for debugging purposes, make it easier to visualize a TrieNode
        public String toString() {
            String ret = "prefix=";
            ret += pref + "; ";
            if (isWord) {ret += "is a word\n";}
            else {ret += "is not a word\n";}
            ret += "marked: ";
            for (int i=0; i<marked.length; i++) {
                ret += ((marked[i]) ? "T" : "F");
            }
            return ret;
        }
    }

    //this is necessary for knowing the immediate position
    //for some letter inside the TrieNode
    private HashMap<Character, Integer> letter_to_pos;
    private int num_letters;
    private char letters; //all possible letters (e.g. only ACGT)
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private TrieNode root;

    public MyTrie() {
        num_letters = 26;
        letter_to_pos = new HashMap<Character, Integer>();
        for (int i=0; i<26; i++) {
            letter_to_pos.put(alphabet.charAt(i), i);
        }
        root = new TrieNode(num_letters, "");
    }
    public MyTrie(int L, char[] letters) {
        if (L != letters.length) {
            System.out.println("This is impossible!"); return;
        }
        num_letters = L;
        letter_to_pos = new HashMap<Character, Integer>();
        for (int i=0; i<L; i++) {
            letter_to_pos.put(letters[i], i);
        }
        root = new TrieNode(L, "");
    }

    public void insert(String word) {
        TrieNode curNode = root;
        String curPref = "";
        for (int i=0; i<word.length(); i++) {
            char let = word.charAt(i);
            curPref += let;
            int mapped_idx = letter_to_pos.get(let);
            if (curNode.marked[mapped_idx]) {
                //simply move down to already established TrieNode
                curNode = curNode.child[mapped_idx];
            }
            else {
                //create a new TrieNode at this specific letter for this specific pref
                curNode.marked[mapped_idx] = true;
                curNode.child[mapped_idx] = new TrieNode(num_letters, curPref);
                curNode = curNode.child[mapped_idx];
            }
        }
    }

    //for now, getting the exact search position will be another task.
    public int search(String word) {
        if (!contains(word)) {
            return -1;
        }
        return 0;
    }

    public boolean remove(String word) {

        return false;
    }

    public boolean contains(String word) {

        return false;
    }

    public boolean containsPrefix(String pref) {
        TrieNode curNode = root;
        for (int i=0; i<pref.length(); i++) {
            char let = pref.charAt(i);
            int mapped_idx = letter_to_pos.get(let);
            if (!curNode.marked[mapped_idx]) {return false;}
            curNode = curNode.child[mapped_idx];
        }
        return true;
    }

    public static void main(String[] args) {
        MyTrie trie = new MyTrie(); //using standard english alphabet
        String[] word_list = {
            "dog", "dove", "done",
            "tree", "frogger", "frozen"
        };
        for (String WORD : word_list) {
            trie.insert(WORD);
        }
        System.out.println(trie.root);
        // System.out.println(trie.contains("frog"));
        // System.out.println(trie.containsPrefix("frog"));
        // System.out.println(trie.containsPrefix("dull"));
        // System.out.println(trie.containsPrefix("dog"));
    }
}