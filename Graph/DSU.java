import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DSU<T> {

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