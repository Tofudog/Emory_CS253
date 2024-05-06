/*
author: Leonardo de Farias

This is a generalized custom Graph
*/

import java.util.*;

//auxiliary custom DSU class
//this is the original, but worse implementation of DSU
class DSU<T> {

	//records direct parent of parent[i] (but may not be root!)
	private ArrayList<T> parent;
	private HashMap<T, T> parent_idx;
	private HashMap<T, Integer> rank;

	public DSU(ArrayList<T> par) {
		parent = par;
		parent_idx = new HashMap<T, T>();
		rank = new HashMap<T, Integer>();
		for (int i=0; i<par.size(); i++) {
			//each node is its own component
			//and therefore its own root.
			parent_idx.put(par.get(i), par.get(i));
			rank.put(par.get(i), 1);
		}
	}

	//find out the root parent of p1
	public T find(T p1) {
		if (parent_idx.get(p1).equals(p1)) {
			return p1;
		}
		T root = find(parent_idx.get(p1));
		parent_idx.put(p1, root);
		return root;
	}

	public void merge(T p1, T p2) {
		//to improve complexity, figure out rank
		T par1 = find(p1);
		T par2 = find(p2);
		if (!par1.equals(par2)) {
			if (rank.get(par1) <= rank.get(par2)) {
				parent_idx.put(par1, par2);
			}
			else {
				parent_idx.put(par2, par1);
			}
			if (rank.get(par1) == rank.get(par2)) {
				rank.put(par2, rank.get(par2)+1);
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ArrayList<String> arr = new ArrayList<>();
		for (int i=0; i<n; i++) {
			String node = sc.next();
			arr.add(node);
		}
		DSU<String> dsu = new DSU<String>(arr);
		int q = sc.nextInt();
		while (q>0) {
			int form = sc.nextInt();
			String u = sc.next(); String v = sc.next();
			if (form == 1) {
				//arbitrarily, we'll say this is merge form
				dsu.merge(u, v);
			}
			else {
				//check if u can reach v
				System.out.println(
					(dsu.find(u).equals(dsu.find(v))) ? "YES\n" : "NO\n"
				);
			}
			q--;
		}
	}
}

/*
My own custom Graph that supports basic applications.
--> Weighted or unweighted
--> Directed or undirected
--> BFS shortest path
--> Many classic shortest path algorithms on weighted graphs
*/
public class MyGraph<V, E> {
	public class Vertex<V> {
		public V element;
		public HashMap<Vertex<V>, Edge<E>> outgoing;
		public HashMap<Vertex<V>, Edge<E>> incoming;
		public Vertex(V element) {
			this.element = element;
			outgoing = new HashMap<Vertex<V>, Edge<E>>();
			if (unDirected) {incoming = outgoing;}
			else {incoming = new HashMap<Vertex<V>, Edge<E>>();}
		}
	}

	public class Edge<E> {
        public E element;
        public E[] elements = (E[]) new Object[2];
        public Vertex<V>[] endpoints;
        public Edge(Vertex<V> u, Vertex<V> v, E element){
            this.element = element;
            endpoints = new Vertex[]{u,v};
        }
	}

	//make sure to allow for Double type weighted edges
	public class EdgeComparator implements Comparator<Edge<E>> {
		public int compare(Edge<E> e1, Edge<E> e2) {
			Integer v1 = (Integer)e1.element;
			Integer v2 = (Integer)e2.element;
			if (v1 >= v2) {return 1;}
			else {return -1;}
		}
	}

	public class Pair {
		public Integer first;
		public Vertex<V> second;
		public Pair(Integer first, Vertex<V> second) {
			this.first = first;
			this.second = second;
		}
	}

	public class PairComparator implements Comparator<Pair>{
        public int compare(Pair p1, Pair p2) {
            if (p1.first < p2.first) {return 1;}
            else {return -1;}
        }
	}


	private int INF;
	private boolean unDirected;
	private HashMap<V, Vertex<V>> vertices;
	private ArrayList<Vertex<V>> vertex_list;
	private ArrayList<Edge<E>> edge_list;
	public MyGraph() {
		unDirected = false;
		vertices = new HashMap<V, Vertex<V>>();
		vertex_list = new ArrayList<Vertex<V>>();
		edge_list = new ArrayList<Edge<E>>();
		INF = 10000;
	}
	public MyGraph(boolean unDirected) {
		this.unDirected = unDirected;
		vertices = new HashMap<V, Vertex<V>>();
		vertex_list = new ArrayList<Vertex<V>>();
		edge_list = new ArrayList<Edge<E>>();
		INF = 10000;
	}

	public void addVertex(V name) {
		Vertex<V> vertex = new Vertex<V>(name);
		vertices.put(name, vertex);
		vertex_list.add(vertex);
	}
	public Vertex<V> getVertex(V name) {
		return vertices.get(name);
	}
	public void printVertices() {
		for (Vertex<V> vert : vertex_list) {
			System.out.println(vert.element);
		}
	}

	public void addEdge(V nameA, V nameB, E weight) {
		Vertex<V> vertexA = getVertex(nameA);
		Vertex<V> vertexB = getVertex(nameB);
		Edge<E> edge = new Edge<E>(vertexA, vertexB, weight);
		edge_list.add(edge);
		vertexA.outgoing.put(vertexB, edge);
		vertexB.incoming.put(vertexA, edge);
	}
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v){
        return u.outgoing.get(v);
    }

    //theoretically, doing dist check
    //could be constant with variables checking levels
	public int BFS(V nameA, V nameB) {
		if (!vertices.containsKey(nameA) ||
			!vertices.containsKey(nameB)) {
			//throw new java.lang.Error("Both keys must exist!");
			System.out.println("Both keys must exist!");
			return -1;
		}
		if (nameA.equals(nameB)) {
			return 0;
		}
		HashMap<Vertex<V>, Integer> DIST = new HashMap<Vertex<V>, Integer>();
		DIST.put(this.vertices.get(nameA), 0);
		HashSet<Vertex<V>> visited = new HashSet<Vertex<V>>();
		visited.add(this.vertices.get(nameA));
		Queue<Vertex<V>> q = new LinkedList<Vertex<V>>();
		q.add(this.vertices.get(nameA));
		while (!q.isEmpty()) {
			Vertex<V> cur = q.poll();
			if (cur.element.equals(nameB)) {
				break;
			}
			for (Edge<E> edge : cur.outgoing.values()) {
				Vertex<V> other = edge.endpoints[0];
				if (other.equals(cur)) {
					other = edge.endpoints[1];
				}
				if (!visited.contains(other)) {
					q.add(other);
					visited.add(other);
					DIST.put(other, DIST.get(cur)+1);
				}
			}
		}
		if (!DIST.containsKey(this.vertices.get(nameB))) {
			System.out.println("Can't reach " + nameB + " from " + nameA);
			return -1;
		}
		return DIST.get(this.vertices.get(nameB));
	}

	//for now, it's just to another vertex (can be applied to all quicker)
	public int shortestWeight(V nameA, V nameB) {
		if (!vertices.containsKey(nameA) ||
			!vertices.containsKey(nameB)) {
			//throw new java.lang.Error("Both keys must exist!");
			System.out.println("Both keys must exist!");
			return -1;
		}
		HashSet<Vertex<V>> visited = new HashSet<>();
		HashMap<Vertex<V>, Integer> DIST = new HashMap<Vertex<V>, Integer>();
		PriorityQueue<Pair> pq = new PriorityQueue<>(
			100000, new PairComparator()
		);
		//initialize all distances to infinity except to source
		for (Vertex<V> vertex : this.vertices.values()) {
			if (vertex == this.vertices.get(nameA)) {
				DIST.put(vertex, 0);
				pq.add(new Pair(0, vertex));
			}
			else {
				DIST.put(vertex, INF);
				pq.add(new Pair(INF, vertex));
			}
		}

		while (!pq.isEmpty()) {
			Pair curEntry = pq.poll();
			Integer curD = curEntry.first;
			Vertex<V> curV = curEntry.second;
			visited.add(curV);
			for (Vertex<V> neighbor : curV.outgoing.keySet()) {
				if (!visited.contains(neighbor)) {
					Integer delta = DIST.get(curV) + 
						(Integer)curV.outgoing.get(neighbor).element;
					if (DIST.get(neighbor) > delta) {
						DIST.put(neighbor, delta);
						pq.add(new Pair(delta, neighbor));
					}
				}
			}
		}

		return DIST.get(this.vertices.get(nameB));
	}

	//custom MST (allows choosing between Kruskal's and Prim's)
	public int MST() {
		Collections.sort(edge_list, new EdgeComparator());
		int w_ret = 0;

		DSU<Vertex<V>> dsu = new DSU<Vertex<V>>(vertex_list);
		for (Edge<E> edge : edge_list) {

			Vertex<V> vA = edge.endpoints[0];
			Vertex<V> vB = edge.endpoints[1];
			System.out.println(vA.element + " <---> "
				+ vB.element + " weighted " + edge.element);
			int w_cur = (Integer)edge.element;

			if (dsu.find(vA) != dsu.find(vB)) {
				dsu.merge(vA, vB);
				w_ret += w_cur;
			}

		}

		return w_ret;
	}

	public static void main(String[] args) {
		MyGraph G = new MyGraph<String, Integer>(true);
		String places[] = {
			"Lubbock", "El Paso", "Denver",
			"Dallas", "Houston", "Atlanta",
			"Rio de Janeiro", "COMPLEX"
		};
		for (String P : places) {
			G.addVertex(P);
		}
		G.addEdge("Lubbock", "El Paso", 1);
		G.addEdge("Lubbock", "Denver", 1);
		G.addEdge("Lubbock", "Dallas", 1);
		G.addEdge("Lubbock", "Houston", 1);
		G.addEdge("Houston", "Rio de Janeiro", 99);
		G.addEdge("Denver", "Rio de Janeiro", 12);
		G.addEdge("Dallas", "Atlanta", 1);
		G.addEdge("Atlanta", "COMPLEX", 1);	
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		while (T>0) {
			T--;
			System.out.println("Choose your starting point (A) and destination (B)");
			System.out.print("(A): ");
			String ptA = sc.nextLine();
			System.out.println();
			System.out.print("(B): ");
			String ptB = sc.nextLine();
			System.out.println();
			//then do BFS here... print out integer value and actual path
			int DIST1 = G.BFS(ptA, ptB);
			System.out.println("DIST from " + ptA +
				" to " + ptB + " is " + DIST1 + " (unweighted)");
			System.out.println();
			int DIST2 = G.shortestWeight(ptA, ptB);
			System.out.println("DIST from " + ptA +
				" to " + ptB + " is " + DIST2 + " (weighted)");
			System.out.println();
		}
		int mst_dist = G.MST();
		System.out.print("What is the MST? ");
		System.out.println(mst_dist);
	}
}
