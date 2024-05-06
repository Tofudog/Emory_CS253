/*
author: Leonardo de Farias

Classic DSU Data Structure that allows various
types and supports merging and finding. Both
union by size and union by rank implementations are shown.
*/

import java.util.ArrayList;
import java.util.HashMap;//each node has associated par, so ideal ds

public class DSU<T> {

    private HashMap<T, T> parent;
    private HashMap<T, Integer> size;
    private HashMap<T, Integer> rank;

    public DSU() {
        parent = new HashMap<T, T>();
        size = new HashMap<T, Integer>();
        rank = new HashMap<T, Integer>();
    }

    //initializes a tree for a new node
    public void make_set(T value) {
        if (parent.containsKey(value)) {
            return;
        }
        parent.put(value, value);
        size.put(value, 1);
        rank.put(value, 1);
    }

    //returns the representative of a node
    public T find(T A) {
        if (parent.get(A).equals(A)) {
            return A;
        }
        T rep = this.find(parent.get(A));
        return rep;
    }

    //merges two nodes such that each has same representative
    public void merge(T A, T B) {
        T rep1 = this.find(A);
        T rep2 = this.find(B);

        if (!rep1.equals(rep2)) {
            //union by size
            // if (size.get(rep1) > size.get(rep2)) {
            //     parent.replace(rep2, rep1);
            //     size.replace(rep1, size.get(rep1)+size.get(rep2));
            // }
            // else {
            //     parent.replace(rep1, rep2);
            //     size.replace(rep2, size.get(rep2)+size.get(rep1));
            // }
            //union by rank
            if (rank.get(rep1) > rank.get(rep2)) {
                parent.replace(rep2, rep1);
            }
            else if (rank.get(rep1) < rank.get(rep2)) {
                parent.replace(rep1, rep2);
            }
            else {
                parent.replace(rep2, rep1);
                rank.replace(rep1, rank.get(rep1)+1);
            }
        }
    }

    //checking if value is even in the dsu
    public boolean contains(T value) {
        return parent.containsKey(value);
    }

    public String toString() {
        String ret = "";
        HashMap<T, ArrayList<T>> components = new HashMap<T, ArrayList<T>>();
        for (T A : parent.keySet()) {
            T rep = this.find(A);
            if (!components.containsKey(rep)) {
                components.put(rep, new ArrayList<T>());
            }
            //for efficiency, make sure cur_component is not compied to new arr (i.e. pointer)
            ArrayList<T> cur_component = components.get(rep);
            cur_component.add(A);
            components.replace(rep, cur_component);
        }
        for (T rep : components.keySet()) {
            for (T node : components.get(rep)) {
                System.out.print(node + " --- ");
            }
            System.out.println();
        }
        return ret;
    }

    public static void main(String[] args) {
        DSU<String> dsu = new DSU<String>();
        String places[] = {
            "Lubbock", "Amarillo",
            "Dallas", "Houston",
            "Denver", "Atlanta",
            "COMPLEX", "North Korea",
            "Japan", "South Korea"
        };
        for (String PLACE : places) {
            dsu.make_set(PLACE);
        }

        System.out.println(dsu.find("Lubbock"));
        System.out.println(dsu.find("North Korea"));

        dsu.merge("Lubbock", "Amarillo");
        dsu.merge("Lubbock", "Dallas");
        dsu.merge("Dallas", "Houston");
        dsu.merge("Atlanta", "COMPLEX");
        dsu.merge("Japan", "South Korea");

        //can we get from Amarillo to North Korea or vice-versa? South Korea?
        System.out.println(dsu.find("Lubbock"));
        System.out.println(dsu.find("North Korea"));
        System.out.println(dsu.find("South Korea"));

        System.out.println(dsu);
    }
}