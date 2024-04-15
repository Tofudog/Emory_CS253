import java.util.HashMap;

/*
author: Leonardo de Farias

Below is an implementation of a Trie from scratch.
It supports the following fundamental operations:
---> insert: put in a word of length 'L' into the trie
---> search: find a word in the trie
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
        
    }

    //this is necessary for knowing the immediate position
    //for some letter inside the TrieNode
    private HashMap<char, int> letter_to_pos;
    private int num_letters;
    private char letters; //all possible letters (e.g. only ACGT)
    private const final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public MyTrie() {
        num_letters = 26;
        letter_to_pos = new HashMap<char, int>();
        for (int i=0; i<26; i++) {
            letter_to_pos.put((char)alphabet.get(i), i);
        }
    }
    public MyTrie(int L, char[] letters) {
        if (L != letters.size()) {
            System.out.println("This is impossible!"); return;
        }
        num_letters = L;
        letter_to_pos = new HashMap<char, int>();
        for (int i=0; i<L; i++) {
            letter_to_pos.put(letters[i], i);
        }
    }

    public void insert(String word) {

    }

    public int search(String word) {

    }

    public boolean remove(String word) {

    }

    public static void main(String[] args) {

    }
}