//import HeapBasedPQ.java

/*
author: Leonardo de Farias

This is my collection of PQ implementations using both
arrays and doubly LLs. The implementations are tested on
sorted and unsorted lists. I also use another implementation
of PQ (heap-based), which is more efficient.

I will implement my priority queue based on the methods available on the
documentation: https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html

Time complexities:
    n: cur number of nodes
    h: cur height of tree
    --> push: O(h) = O(log(n))
    --> remove: O(h) = O(log(n))
    --> indexing: O(1)

Notes to improve:
    --> figure out comparing generics (for now only Integers)
*/

public class MyPriorityQueue<V> {

    private Object heap[]; //easily define parent/child indexing, efficient
    private int size;
    private int backIdx; //last index of element placed
    
    public MyPriorityQueue() {
        size = 0;
        heap = new Object[1000];
        backIdx = -1;
    }

    public MyPriorityQueue(int space) {
        size = 0;
        heap = new Object[space];
        backIdx = -1;
    }

    //formulas for accessing subtrees/parents
    public int getParent(int idx) {
        return (idx-1)/2;
    }
    public int getLeftChild(int idx) {
        return 2*idx + 1;
    }
    public int getRightChild(int idx) {
        return 2*idx + 2;
    }

    //place at new backIdx and "swim up" until its subtree is larger
    public void push(V val) {
        heap[++backIdx] = val;
        int cur = backIdx;
        int par = this.getParent(cur);
        if (backIdx != 0) {
            while (cur != 0 && ((Integer)heap[cur]).compareTo((Integer)heap[par]) < 0 ) {
                this.swap(cur, par);
                cur = par;
                par = this.getParent(par);
            }
        }
    }

    //does the same thing as poll() except without returning value
    public void pop() {
        V top = this.poll();
    }

    //remove top and restructure the heap: O(log(n))
    public V poll() {
        V ret = (V)heap[0];
        this.swap(backIdx, 0); backIdx--;
        int cur = 0;
        while (cur > backIdx) {
            int left = this.getLeftChild(cur);
            int right = this.getRightChild(cur);
            if (((Integer)heap[cur]).compareTo((Integer)heap[left]) <= 0 &&
                        ((Integer)heap[cur]).compareTo((Integer)heap[right]) <= 0) {
                break;
            }
            int biggerIdx = left;
            if (((Integer)heap[right]).compareTo((Integer)heap[left]) > 0) {biggerIdx = right;}
            this.swap(cur, biggerIdx);
            cur = biggerIdx;
        }
        return ret;
    }

    //since not modifying the heap, only O(1) operation
    public V top() {
        //make warning or error
        if (backIdx < 0) {return null;}
        return (V)heap[0];
    }


    //specifically for swapping two values in heap
    public void swap(int idx1, int idx2) {
        V temp = (V)heap[idx1];
        heap[idx1] = (V)heap[idx2];
        heap[idx2] = temp;
    }

    public int size() {
        return backIdx+1;
    }

    /*
    This will require more space to implement, as
    need another priority queue to not mess with
    currently defined one.
    */
    public String toString() {
        return "The priority queue has length: " + this.size();
    }

    public static void main(String[] args) {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<Integer>();
        pq.push(3);
        pq.push(7);
        pq.push(12);
        pq.push(4);
        System.out.println(pq.top());
        pq.push(2);
        System.out.println(pq.top());
        System.out.println(pq.poll());
        pq.pop();
        System.out.println(pq.poll());
    }
}