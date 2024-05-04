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
        public String toString() {
            String ret = "";
            if (parent == null) {
                ret += "\tnull\n\t|";
            } else {ret += "\t"+parent.data+"\n\t|";}
            ret += ("\n\t"+data+"\n\t<- ->");
            if (left == null) {
                ret += ("\n\tnull  ");
            } else {ret += ("\n\t"+left.data+"  ");}
            if (right == null) {
                ret += "null";
            } else {ret += right.data;}
            return ret;
        }
    }

    public Node<T> root;
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
        Node<T> curParent = null;
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
            curParent = curNode;
        }

        //place newNode at left of curNode
        if (this.compare(curNode.data, data) > 0) {
            curNode.left = newNode;
        }
        else {
            curNode.right = newNode;
        }
        newNode.parent = curNode;

        System.out.println(newNode + "\n\n");

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
        if (curNode != null) {
            System.out.println(curNode.data + "..." + this.depth(curNode));
        }
        return found;
    }

    //deletes a node in O(h)
    public boolean remove(T data) {
        boolean found = false;
        
        Node<T> curNode = root;
        while (curNode != null) {

            if (this.compare(curNode.data, data) > 0) {
                curNode = curNode.left;
            }
            else if (this.compare(curNode.data, data) < 0) {
                curNode = curNode.right;
            }
            else {
                //then the data has been found
                //now handle three separate cases (0,1,2 children)
                found = true;

                int num_child = 0;
                if (curNode.left!=null) {num_child++;}
                if (curNode.right!=null) {num_child++;}

                boolean isRoot = (curNode == root);
                boolean isLeft = (curNode.parent!=null && curNode.parent.left == curNode);
                boolean isRight = (curNode.parent!=null && curNode.parent.right == curNode);

                if (num_child == 0) {
                   System.out.println(curNode);
                    if (isRoot) {
                        root = null;
                    }
                    else if (isLeft) {
                        curNode.parent.left = null;
                    }
                    else {
                        curNode.parent.right = null;
                    }
                    System.out.println(data + " node has no children");
                }
                else if (num_child == 1) {
                    Node<T> childNode = (curNode.left!=null) ? curNode.left:curNode.right; 
                    if (isRoot) {
                        root = childNode;
                        root.parent = null;
                    }
                    else if (isLeft) {
                        curNode.parent.left = childNode;
                    }
                    else {
                        curNode.parent.right = childNode;
                    }
                    System.out.println(data + " node has 1 child");
                }
                else {
                    //binary tree structure is disrupted
                    System.out.println(data + " node has 2 child");
                }

                break;
            }

        }

        num_nodes--;
        return found;
    }

    //finds depth of some node in O(h)
    public int depth(Node<T> node) {
        int DEPTH = 0;
        Node<T> curNode = node;
        while (curNode != root) {
            curNode = curNode.parent;
            DEPTH++;
        }
        return DEPTH;
    }

    //finds maximum depth of subtree node in O(n)
    public int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        int height_subtree1 = this.height(node.left);
        int height_subtree2 = this.height(node.right);
        return 1 + Math.max(height_subtree1, height_subtree2);
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
        bt.insert("don");

        System.out.println(bt.search("fr"));
        System.out.println(bt.search("frog"));
        System.out.println(bt.search("do"));

        String strs[] = {"apples", "do", "lem", "lemon"};
        for (String STR : strs) {
            System.out.println(
                (bt.remove(STR)) ? "removed "+STR :
                                   STR + " not found"
            );
        }

        System.out.println(bt.search("lemon"));
        System.out.println(bt.search("dew"));

        System.out.println("depth of root: " + bt.depth(bt.root));
        System.out.println("height of root: " + bt.height(bt.root));

    }
}