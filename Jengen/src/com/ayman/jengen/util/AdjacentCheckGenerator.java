package com.ayman.jengen.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to generate the pairs of adjacent coordinates which need to be checked for connectivity.
 * This will be used in the RandomLevel class to generate a valid level with no isolated areas.
 * */
public class AdjacentCheckGenerator {

    public static final List<Vector2i[]> vectors = new ArrayList<>();



    static {

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {

                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++) {
                        if(!adjacent(new Vector2i(i, j), new Vector2i(k, l)))
                            vectors.add( new Vector2i[] {new Vector2i(i, j), new Vector2i(k, l)});
                    }
                }
            }
        }



    }

    public static boolean adjacent(Vector2i v1, Vector2i v2) {
        return Math.abs(v1.getX() - v2.getX()) <= 1 && Math.abs(v1.getY() - v2.getY()) <= 1;
    }

    public static void main(String[] args) {
        for(Vector2i[] vector : vectors) {
            System.out.println(vector[0] + " " + vector[1]);
        }
    }
}
