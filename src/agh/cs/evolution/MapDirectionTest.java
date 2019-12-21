package agh.cs.evolution;

import org.junit.Test;

public class MapDirectionTest {

    @Test
    public void testRotateRight() {
        for (MapDirection dir : MapDirection.values())
            assert dir.rotateRight(0) == dir;

        for (MapDirection dir : MapDirection.values())
            assert dir.rotateRight(4).rotateRight(4) == dir;

        assert MapDirection.N.rotateRight(2) == MapDirection.E;
        assert MapDirection.E.rotateRight(4) == MapDirection.W;
        assert MapDirection.SW.rotateRight(7) == MapDirection.S;
    }
}