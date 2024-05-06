/*
author: Leonardo de Farias

This is an implementation of the Ford-Fulkerson
algorithm from scratch. This implementation only
allows nodes that are integers, and does not provide
functionality for s-t cut.
*/

import java.util.*;

public class MaxFlow {

	private int numV;
	private int graph[][];
	private int rgraph[][];
	private int par[];

	public MaxFlow(int numV, int graph[][]) {
		this.numV = numV;
		this.graph = graph;
		rgraph = new int[numV][numV];
		par = new int[numV];
	}

	public boolean bfs(int s, int t) {
		for (int u=0; u<numV; u++) {
			par[u] = -1;
		}
		par[s] = -2;

		LinkedList<Integer> q = new LinkedList<>();
		q.addLast(s);

		while (q.size()>0) {

			int u = q.pollFirst();
			for (int v=0; v<numV; v++) {

				if (par[v]==-1 && rgraph[u][v] > 0) {
					if (v==t) {
						par[t] = u;
						return true;
					}
					par[v] = u;
					q.addLast(v);
				}

			}

		}
		return false;
	}

	public int answer(int s, int t) {
		int flow = 0;

		for (int i=0; i<numV; i++) {
			for (int j=0; j<numV; j++) {
				rgraph[i][j] = graph[i][j];
			}
			par[i] = -1;
		}

		//while there is an augmenting path,
		//find min residual and add to flow
		int cycles = 0;
		while (bfs(s, t)) {

			ArrayList<Integer> path = new ArrayList<>();
			for (int u=t; u!=s; u=par[u]) {
				path.add(u);
			}
			path.add(s);

			int minres = Integer.MAX_VALUE;
			for (int i=0; i<path.size()-1; i++) {
				int v = path.get(i);
				int u = path.get(i+1);
				minres = Math.min(minres, rgraph[u][v]);
				System.out.println(u + " ---> " + v);
			}

			flow += minres;

			for (int i=0; i<path.size()-1; i++) {
				int v = path.get(i);
				int u = path.get(i+1);
				rgraph[u][v] -= minres;
				rgraph[v][u] += minres;
			}

			System.out.println(minres);
			System.out.println();

			// if (cycles==1) {break;}
			cycles++;

		}

		return flow;

	}

	public static void main(String[] args) {
		int G[][] = new int[][] {
			{0, 8, 9, 0, 0, 0, 0},
			{0, 0, 0, 0, 6, 2, 0},
			{0, 2, 0, 4, 0, 0, 0},
			{0, 8, 9, 0, 0, 0, 3},
			{0, 0, 0, 0, 0, 0, 6},
			{0, 0, 0, 0, 0, 0, 6},
			{0, 0, 0, 0, 0, 0, 0},
		};
		MaxFlow flow = new MaxFlow(7, G);
		System.out.println(flow.answer(0,6));
	}
}