package com.ayman.fightEnemies.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DSUTest {

    static DSU dsu;
    @BeforeAll
    static void setUp() {
        dsu = new DSU(10);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void find() {

        dsu.union(0, 1);
        dsu.union(1, 2);
        dsu.union(2, 3);
        assertTrue(dsu.find(0) == dsu.find(3));
    }

    @Test
    void union() {
        dsu.union(0, 1);
        assertTrue(dsu.find(0) == dsu.find(1));
    }

    @Test
    void connected() {

    }

    @Test
    void count() {
    }
}