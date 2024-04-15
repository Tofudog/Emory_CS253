import java.util.Queue;

/*
author: Leonardo de Farias

Simple queue implemented from scratch.
Most features of this class can be found at the
following documentation page: https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html,
which is an interface.

This is an array-based implementation of a queue.
An LL-based one is very straightforward.
*/

interface QueueInterface<T> {
    public void add(Object val);
    public void pop();
    public T poll();
}

public class MyQueue<T> implements QueueInterface<T> {

    private Object elements[];
    private int size;
    private int front;
    private int back;
    
    public MyQueue() {
        elements = new Object[1000];
        size = 0;
        front = 0;
        back = 0;
    }
    public MyQueue(int space) {
        size = space;
        elements = new Object[size];
        front = 0;
        back = 0;
    }

    //implementing all the expected interface methods
    public void add(Object val) {
        if (back==size) {
            //then double the size of the array
            this.expandArray();
        }
        elements[back++] = val;
    }
    public void pop() {
        front++;
    }
    public T poll() {
        @SuppressWarnings("unchecked")
        T ret = (T)elements[front++];
        return ret;
    }

    //increasing the size of the auxiliary array by a factor of 2
    public void expandArray() {
        Object newELements[] = new Object[2*size];
        for (int i=0; i<size; i++) {
            newELements[i] = elements[i];
        }
        elements = newELements;
        size *= 2;
    }

    //clearing will be as easy as setting the front and back
    public void clear() {
        front = back = 0;
    }

    //simple visualization of the queue
    public String toString() {
        String ret = "Order in line: ";
        for (int i=front; i<back; i++) {
            @SuppressWarnings("unchecked")
            T val = (T)elements[i];
            ret += (String)val; ret += " ... ";
        }
        return ret;
    }

    public static void main(String[] args) {
        MyQueue<String> q = new MyQueue<String>();
        q.add("Splinter");
        q.add("April");
        q.add("Rafael");
        q.add("Donatello");
        q.add("Leonardo");
        q.add("Michelangelo");
        System.out.println(q);
        q.pop(); q.pop();
        System.out.println(q);
        q.add("Brain");
        q.add("Shredder");
        q.pop(); q.pop();
        String chosen = q.poll();
        System.out.println(q);
        System.out.println(chosen + " is the chosen one!");
    }
}