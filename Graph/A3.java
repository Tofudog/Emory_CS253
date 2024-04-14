/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Leonardo de Farias
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class A3 {
    
    private static HashSet<TreeNode> evens;

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val;}
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
     }

    public static TreeNode deleteTree(TreeNode root, TreeNode og) {
        if (root==og) {evens = new HashSet<TreeNode>();}
        if (root==null) {return null;}
        TreeNode leftRoot = deleteTree(root.left, og);
        TreeNode rightRoot = deleteTree(root.right, og);
        if (root.val%2==0 && (leftRoot==null||evens.contains(leftRoot))
            && (rightRoot==null||evens.contains(rightRoot))) {
            evens.add(root);
        }
        return root;
    }

    public static void main(String[] args) throws IOException {
        // Read input array A. We avoid java.util.Scanner, for speed.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // first line
        Integer[] A = new Integer[N];
        StringTokenizer st = new StringTokenizer(br.readLine()); // second line
        for (int i=0; i<N; ++i) {
            String s = st.nextToken();
            A[i] = (s.equals("null") ? null : Integer.parseInt(s));
        }
        
        // Create the input binary tree
        TreeNode root = new TreeNode();
        if (A[0] == null) {
            root = null;
        }
        else { 
            int count = 0;
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            root = new TreeNode(A[0]);
            q.add(root);
            TreeNode cur = null;
            for(int i = 1; i < A.length; i++){
                TreeNode node = new TreeNode();
                if (A[i] == null) {
                    node = null;
                } else {
                    node = new TreeNode(A[i]);
                }
                if(count == 0){
                   cur = q.poll();           
                }
                if(count==0){
                  count++;
                  cur.left = node;
                }else {
                  count = 0;
                  cur.right = node;
                }
                if(A[i] != null){
                  q.add(node);
                }
             }
        }

        root = deleteTree(root, root);

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            //if (node != null) {System.out.println(node);}
            if (evens.contains(node)) {
                //System.out.println(node.val);
                node.val = -1;
                continue;
            }
            if (node==null) {continue;}
            TreeNode lchild = node.left;
            TreeNode rchild = node.right;
            q.add(lchild);
            q.add(rchild);
        }

        PrintWriter out = new PrintWriter(System.out);
        
        ArrayList<TreeNode> answers = new ArrayList<>();
        if (root==null || root.val==-1) {out.print("null ");}
        else {
            Queue<TreeNode> cur = new LinkedList<TreeNode>();
            cur.add(root);
            while (!cur.isEmpty()) {
                TreeNode node = cur.poll();
                if (node==null||node.val==-1) {answers.add(null);}
                else {
                    answers.add(node);
                }
                if (node!=null&&node.val!=-1) {
                    cur.add(node.left);
                    cur.add(node.right);
                }

            }
            int lastidx = 0;
            for (int i=0; i<answers.size(); i++) {
                TreeNode node = answers.get(i);
                if (node!=null && node.val!=-1) {
                    lastidx = i;
                }
            }
            for (int i=0; i<=lastidx; i++) {
                TreeNode node = answers.get(i);
                if (node==null || node.val==-1) {
                    out.print("null ");
                }
                else {
                    out.print(node.val + " ");
                }
            }
        }

        out.close();

    }
}