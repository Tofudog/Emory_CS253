/*
author: Leonardo de Farias
--> Singly LL
--> Doubly LL
--> Circular
*/

public class LinkedList<T> {

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

    //this will arbitrarily remove the tail
    public void remove() {

    }
    public void remove(int pos) {
        if (pos > size) {
            //throw new Exception("Index out of bounds! Size is only " + size);
            return;
        }
    }
    public void removeTail() {
        this.remove();
    }
    public void removeHead() {

    }

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
        LinkedList<String> LL = new LinkedList<String>("singly");
        LL.add("Lubbock");
        LL.add("Dallas");
        LL.add("Atlanta");
        System.out.println(LL);
    }
}