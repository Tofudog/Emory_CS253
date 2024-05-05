/*
author: Leonardo de Farias

Implementation of Minimum Spanning Trees from scratch,
specifically Kruskal's and Prim's. Edges will be integers.

THIS WILL BE TESTED AND DEBUGGED LATER!!!
*/

import DSU.java;

public class MST<T> {

    static class Edge<T, T> {
        public T first;
        public T second;
        public Integer weight;
        public Edge(T first, T second, T W) {
            this.first = first;
            this.second = second;
            weight = W;
        }
    }

    private HashMap<T, ArrayList<T>> graph;
    private ArrayList<Edge<T, T>> edges;
    private DSU<T> dsu;

    public MST() {
        graph = new HashMap<T, ArrayList<T>>();
        edges = new ArrayList<Edge<T, T>>();
        dsu = new DSU<T>();
    }

    public void addEdge(T val1, T val2, Integer W) {
        Edge<T, T> edge = new Edge<T, T>(val1, val2, W);
        edges.add(edge);
    }

    public int kruskal() {
        int weight_sum = 0;

        //sort the edges by weight nondecreasing order...
        Collections.sort(edges, new Comparator<Edge<T,T>>() {
            @Override
            public int compare(Edge<T, T> edge1, Edge<T, T> edge2) {
                if (edge1.weight >= edge2.weight) {return 1;}
                return -1;
            }
        });

        for (Edge<T, T> EDGE : edges) {
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

    public int prim() {
        int weight_sum = 0;

        return weight_sum;
    }

    public static void main(String[] args) {
        MST<String> mst = new MST();
        
    }
}