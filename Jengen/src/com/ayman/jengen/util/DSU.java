package com.ayman.jengen.util;

/**
 * This class is used to check the connectivity of the level graph.
 */
public class DSU {
    private final int[] parent;
    private final int[] rank;
    private int count; // number of components

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int p) {
        if(p == parent[p])
            return p;
        return parent[p] = find(parent[p]);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ)
            return;
        if(rank[rootP] < rank[rootQ])
            parent[rootP] = rootQ;
        else if(rank[rootP] > rank[rootQ])
            parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;

        update();
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public int[] getParent() {
        return parent;
    }

    public void update() {
        for(int i = 0; i < parent.length; i++) {
            parent[i] = find(i);
        }
    }







}
