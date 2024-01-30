package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.util.Vector2i;

public class Node {

    public Vector2i tileCoordinate;
    public Node parent;
    public double f, g, h;

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
        if(node.getTileCoordinate().getX() == this.getTileCoordinate().getX() && node.getTileCoordinate().getY() == this.getTileCoordinate().getY()) return true;
        return false;
    }

    public String toString() {
        return "Node: " + tileCoordinate.toString();
    }






}
