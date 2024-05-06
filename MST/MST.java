/*
author: Leonardo de Farias

Implementation of Minimum Spanning Trees from scratch,
specifically Kruskal's and Prim's. Edges will be integers.

THIS WILL BE TESTED AND DEBUGGED LATER!!!
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;

public class MST<T> {

    class Edge {
        public T first;
        public T second;
        public Integer weight;
        public Edge(T first, T second, Integer W) {
            this.first = first;
            this.second = second;
            weight = W;
        }
        public String toString() {
            String ret = "";
            ret += "   "+weight+"\n";
            ret += first + " --- " + second;
            return ret;
        }
    }

    //private HashMap<T, ArrayList<T>> graph;
    private ArrayList<Edge> edges;
    private DSU<T> dsu;

    public MST() {
        //graph = new HashMap<T, ArrayList<T>>();
        edges = new ArrayList<Edge>();
        dsu = new DSU<T>();
    }

    public void addEdge(T val1, T val2, Integer W) {
        Edge edge = new Edge(val1, val2, W);
        edges.add(edge);
        if (!dsu.contains(val1)) {dsu.make_set(val1);}
        if (!dsu.contains(val2)) {dsu.make_set(val2);}
    }

    public int kruskal() {
        int weight_sum = 0;

        //sort the edges by weight nondecreasing order...
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.weight >= edge2.weight) {return 1;}
                return -1;
            }
        });

        for (Edge EDGE : edges) {
            T rep1 = dsu.find(EDGE.first);
            T rep2 = dsu.find(EDGE.second);
            //check if the two nodes belong to the same component
            if (!rep1.equals(rep2)) {
                dsu.merge(rep1, rep2);
                weight_sum += EDGE.weight;
            }
        }

        return weight_sum;
    }

    //implementations for sparse and dense graphs
    public int prim() {
        return this.primDense();
    }

    //implementation ideal for dense graphs: O(n^2)
    public int primDense() {
        
        return weight_sum;
    }

    //implementation ideal for sparse graphs: O(mlog(n))
    public int primSparse() {
        int weight_sum = 0;
        
        return weight_sum;
    }

    public static void main(String[] args) {
        MST<String> mst = new MST();
        mst.addEdge("a", "b", 1);
        mst.addEdge("b", "c", 1);
        mst.addEdge("a", "c", 5);
        mst.addEdge("a", "d", 4);
        mst.addEdge("c", "d", 8);
        mst.addEdge("c", "e", 2);
        mst.addEdge("c", "f", 6);
        mst.addEdge("d", "f", 7);
        System.out.println(mst.kruskal());
    }
}