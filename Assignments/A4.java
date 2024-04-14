/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Leonardo de Farias
*/

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

	private int numCourses;
	private HashMap<Integer, HashSet<Integer>> graph;
	private HashSet<Integer> visited;
	private HashSet<Integer> recStack;
	private boolean repeats;

	public Result(int n) {
		numCourses = n;
		graph = new HashMap<Integer, HashSet<Integer>>();
		for (int i=0; i<n; i++) {
			graph.put(i, new HashSet<Integer>());
		}
		visited = new HashSet<Integer>();
		recStack = new HashSet<Integer>();
		repeats = false;
	}

    public void addPrereq(int prereq, int course) {
    	graph.get(prereq).add(course);
    }

    public void dfs(int course) {
    	if (recStack.contains(course)) {
    		repeats = true; return;
    	}
    	if (visited.contains(course)) {
    		return;
    	}
    	visited.add(course);
    	recStack.add(course);
    	for (int nextCourse : graph.get(course)) {
    		dfs(nextCourse);
    	}
    	recStack.remove(course);
    }

    //as long as this graph is acyclic, it's possible.
    //a simple dfs on each component will suffice
    public boolean isPossible() {
    	for (int i=0; i<numCourses; i++) {
    		if (!visited.contains(i)) {
    			dfs(i);
    			if (repeats) {
    				repeats = false;
    				return false;
    			}
    		}
    	}
    	visited.clear();
    	return true;
    }
  
}

public class A4 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int numCourses = Integer.parseInt(firstMultipleInput[0]);
        int numPrereqs = Integer.parseInt(firstMultipleInput[1]);

        Result result = new Result(numCourses);
        IntStream.range(0, numPrereqs).forEach(numPrereqsItr -> {
            try {
                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                int prereq = Integer.parseInt(secondMultipleInput[0]);
                int course = Integer.parseInt(secondMultipleInput[1]);
               
                result.addPrereq(prereq, course);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        if (result.isPossible())
            System.out.println("possible");
        else
            System.out.println("IMPOSSIBLE");

        bufferedReader.close();
    }
}