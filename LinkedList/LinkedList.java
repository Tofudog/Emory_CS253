/*
author: Leonardo de Farias

This class allows usage of three types of linked lists:
--> Singly LL
--> Doubly LL
--> Circular

The purpose of this file is to practice writing LL's from scratch.
The features of this class is a subset of those found in the following
documentation page: https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html.
*/

public class LinkedList<T> {

    //custom node class... technically
    //there is no singly option for memory efficiency.
    public static class Node<T> {
        public T data;
        public Node next;
        public Node prev; //always set to null if not circular type
        public Node(T data) {
            this.data = data;
            next = null; prev = null;
        }
    }

    //allow user of this class
    //to choose the type of LL.
    private String kind;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public LinkedList(String kind) {
        if (!(
            kind.equals("singly")||
            kind.equals("doubly")||
            kind.equals("circular")
        )) {
            //throw new Exception(kind + " is not a valid LL type!");
            return;
        }
        this.kind = kind;
        size = 0;
        head = null; tail = null;
    }

    //adds a new node to the end of the list
    public void add(T val) {
        Node<T> newNode = new Node<T>(val);

        if (size==0) {
            head = newNode;
            tail = newNode;
            size = 1; return;
        }

        tail.next = newNode;
        Node<T> prevTail = tail;
        tail = tail.next;

        if (kind.equals("singly")) {}
        else if (kind.equals("doubly")) {
            tail.prev = prevTail;
        }
        else {
            tail.prev = prevTail;
            head.prev = tail;
            tail.next = head;
        }

        size++;
    }

    //inserts a node anywhere in the list
    //such that pos=0 shifts cur head forwards (goes behind cur occupied)
    public void insert(T val, int pos) {
        Node<T> newNode = new Node<T>(val);

        if (pos > size) {
            //throw new Exception("Index out of bounds! Size is only " + size);
            return;
        }
        else if (pos == size) {
            this.add(val);
        }
        else {
            Node<T> prevCur = this.get(pos);
            Node<T> cur = null;
            if (prevCur==null) {
                cur = head;
            }
            else {
                cur = prevCur.next;
            }

            //cur is guaranteed to be the place where newNode will precede
            newNode.next = cur;
            if (!kind.equals("singly")) {
                cur.prev = newNode;
                newNode.prev = prevCur;
            }
            if (prevCur != null) {
                prevCur.next = newNode;
            }
            
            if (pos==0) {
                head = newNode;
            }
        }
        size++;
    }

    //this will arbitrarily remove the tail
    public void remove() {
        this.removeTail();
    }

    //O(n) remove method at some position
    public void remove(int pos) {
        if (pos >= size) {
            //throw new Exception("Index out of bounds! Size is only " + size);
            return;
        }
        if (pos==0) {
            this.clear();
        }
        else if (pos==size-1) {
            this.remove();
        }
        else {
            Node<T> prevCur = this.get(pos);
            Node<T> cur = prevCur.next;
            prevCur.next = cur.next;
            cur.next.prev = prevCur;
        }
    }

    public void removeTail() {
        if (kind.equals("singly")) {
            //this requires special care, as there is no prev field
            this.remove(size-1);
        }
        else {
            if (size==1) {
                this.clear();
            }
            else {
                tail = tail.prev; tail.next = null;
                if (kind.equals("circular")) {
                    head.prev = tail;
                    tail.next = head;
                }
            }
        }
    }

    public void removeHead() {
        if (size==1) {
            this.clear();
        }
        else {
            head = head.next;
            if (!kind.equals("singly")) {
                head.prev = null;
                if (kind.equals("circular")) {
                    head.prev = tail;
                    tail.next = head;
                }
            }
        }
    }

    public void set(T val, int pos) {
        if (pos >= size) {
            return;
        }
        Node<T> cur = this.get(pos);
        if (cur==null) {cur = head;}
        else {cur = cur.next;}
        cur.data = val;
    }

    //gets the node at prev to pos...
    public Node<T> get(int pos) {
        if (pos >= size) {
            return null;
        }
        
        int idx = 0;
        Node<T> cur = head;
        Node<T> prevCur = cur.prev;
        while (idx < pos) {
            prevCur = cur;
            cur = cur.next;
            idx++;
        }
        
        return prevCur;
    }

    public boolean contains(T val) {
        for (Node<T> cur=head; cur!=tail; cur=cur.next) {
            if (cur.data.equals(val)) {
                return true;
            }
        }
        return tail.data.equals(val);
    }

    //empty the list, but in O(1) time
    //not sure if it's important to remove existing in-between data...
    public void clear() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    //this will visualize the list
    public String toString() {
        if (size==0) {
            return "null";
        }
        String ret = "";
        Node<T> cur = head;
        while (cur.next != null && cur.next != head) {
            ret += cur.data;
            ret += (!kind.equals("singly")) ? " <---> " : " ---> ";
            cur = cur.next;
        }
        ret += cur.data; ret += (!kind.equals("singly")) ? " <---> " : " ---> ";
        if (kind.equals("circular")) {ret += head.data;}
        return ret;
    }

    public static void main(String[] args) {
        LinkedList<String> LL = new LinkedList<String>("circular");
        LL.add("Lubbock");
        LL.add("Dallas");
        LL.add("Atlanta");
        System.out.println(LL);
        LL.insert("Houston", 2);
        System.out.println(LL);
        LL.insert("House", 0);
        System.out.println(LL);
        LL.remove();
        System.out.println(LL);
        LL.removeHead();
        System.out.println(LL);
        LL.add("Buffalo");
        LL.add("Seattle");
        LL.remove(2);
        System.out.println(LL);
        LL.set("Nowhere", 3);
        System.out.println(LL);
        System.out.println((LL.contains("Seattle")) ? "YES" : "NO");
    }
}