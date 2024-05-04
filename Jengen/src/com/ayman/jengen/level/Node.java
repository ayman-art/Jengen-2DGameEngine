package com.ayman.jengen.level;

import com.ayman.jengen.util.Vector2i;

/**
 * Node is a class that represents a node. Used in AI pathfinding algorithms.

 */
public class Node {

    public Vector2i tileCoordinate;
    /**
     * The parent is the node that precedes the current node in the path.
     * Used to trace back the path from the end node to the start node.
     */
    public Node parent;

    /**
     * The f is the resultant value of the cost of the path from the start node to the current node and the heuristic value of the current node.
     */
    public double f;
    /**
     * The g is the cost of the path from the start node to the current node.
     */
    public double g;
    /**
     * The h is the heuristic value of the current node.
     */
    public double h;

    public Node(Vector2i tileCoordinate, Node parent, double g, double h) {
        this.tileCoordinate = tileCoordinate;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Node(Vector2i tileCoordinate, double g, double h) {
        this.tileCoordinate = tileCoordinate;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Node(Vector2i tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public Vector2i getTileCoordinate() {
        return tileCoordinate;
    }

    public Node getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public void setTileCoordinate(Vector2i tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public boolean equals(Object object) {
        if(!(object instanceof Node)) return false;
        Node node = (Node) object;
        return node.getTileCoordinate().getX() == this.getTileCoordinate().getX() && node.getTileCoordinate().getY() == this.getTileCoordinate().getY();
    }



    public String toString() {
        return "Node: " + tileCoordinate.toString();
    }






}
