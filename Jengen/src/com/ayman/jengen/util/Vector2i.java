package com.ayman.jengen.util;

/**
 * This class is used to represent a 2D integer vector.

 */
public class Vector2i implements Comparable<Vector2i>{
    private int x, y;

    public Vector2i() {
        setX(0);
        setY(0);
    }

    public Vector2i(int x, int y) {
        setXY(x, y);
    }

    public Vector2i(Vector2i vector) {
        setX(vector.getX());
        setY(vector.getY());
    }

    public Vector2i add(Vector2i vector) {
        return new Vector2i( this.x + vector.getX(), this.y + vector.getY());

    }

    public Vector2i subtract(Vector2i vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
        return this;
    }

    public Vector2i setXY(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Vector2i)) return false;
        Vector2i vec = (Vector2i) object;
        if(vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
        return false;
    }
    @Override
    public int compareTo(Vector2i vector) {
        if(this.getX() != vector.getX())
            return Integer.compare(this.getX(), vector.getX());
        return Integer.compare(this.getY(), vector.getY());
    }


    /**
     * This method is used to calculate the distance between two vectors.
     * @param vector the vector to calculate the distance to.
     * @return the distance between the two vectors.
     */
    public double distanceTo(Vector2i vector) {
        double dx = vector.getX() - this.getX();
        double dy = vector.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }


}
