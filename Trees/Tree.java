/*
author: Leonardo de Farias

Aggregating several different Tree implementations into
this file. Additionally, I wrote a naive normal LL-based
binary search tree with O(h) = O(n) search/insertion
*/

import java.util.*;

class BinaryTree<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T a, T b) {
        return a.compareTo(b);
    }
    public void testCompare(T a, T b) {
        if (this.compare(a, b) > 0) {
            System.out.println(a + " > " + b);
        }
        else if (this.compare(a, b) < 0) {
            System.out.println(a + " < " + b);
        }
        else {
           System.out.println(a + " = " + b); 
        }
    }

    class Node<T> {
        public T data;
        public Node<T> left;
        public Node<T> right;
        public Node<T> parent;
        public Node(T data) {
            this.data = data;
            left = right = parent = null;
        }
    }

    private Node<T> root;
    private int num_nodes;

    public BinaryTree() {
        root = new Node<T>(null);
        num_nodes = 0;
    }

    //quite simply, inserting data into the BST
    //this process takes O(h), where h is the height of root.
    public void insert(T data) {
        Node<T> newNode = new Node<T>(data);
        if (num_nodes == 0) {
            root = newNode;
            num_nodes++;
            return;
        }

        //finish the insertion once the node reaches a leaf.
        Node<T> curNode = root;
        while (!(curNode.left==null && curNode.right==null)) {
            if (this.compare(curNode.data, data) > 0) {
                if (curNode.left == null) {
                    break;
                }
                curNode = curNode.left;
            }
            else {
                if (curNode.right == null) {
                    break;
                }
                curNode = curNode.right;
            }
        }

        //place newNode at left of curNode
        if (this.compare(curNode.data, data) > 0) {
            curNode.left = newNode;
        }
        else {
            curNode.right = newNode;
        }

        num_nodes++;
    }

    //checks whether value is in BST and
    //sequentially visits left/right depending on criteria: O(h)
    public boolean search(T data) {
        boolean found = false;
        System.out.println("querying for " + data);

        Node<T> curNode = root;
        while (true) {
            System.out.println("curNode: " + curNode.data);
            
            if (this.compare(curNode.data, data) > 0) {
                curNode = curNode.left;
            }
            else if (this.compare(curNode.data, data) < 0) {
                curNode = curNode.right;
            }
            else {
                found = true;
                break;
            }

            if (curNode == null) {break;}
        }

        System.out.println();
        return found;
    }

    //returns true whether data can be found and
    //is implemented using DFS instead of ordering: O(h)
    public boolean searchDFS(T data) {

        return false;
    }

    //deletes a node iff it does not have 2 children: O(h)
    public void remove(T data) {

        num_nodes--;
    }

    public int NUM_NODES() {
        return num_nodes;
    }

}

public class Tree {

    public static void main(String[] args) {
        BinaryTree<String> bt = new BinaryTree<String>();
        String examples[][] = {
            {"a", "n"},
            {"r", "g"},
            {"apple", "apples"},
            {"Mo", "Mo"},
        };
        for (int i=0; i<examples.length; i++) {
            bt.testCompare(examples[i][0], examples[i][1]);
        }
        bt.insert("frog");
        bt.insert("apple");
        bt.insert("lemon");
        bt.insert("dew");
        bt.insert("do");
        bt.insert("dont");

        System.out.println(bt.search("fr"));
        System.out.println(bt.search("frog"));
        System.out.println(bt.search("do"));
    }
}