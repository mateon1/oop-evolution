package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

public enum MapDirection {
    N(new Vector2d(0, 1)),
    NE(new Vector2d(1, 1)),
    E(new Vector2d(1, 0)),
    SE(new Vector2d(1, -1)),
    S(new Vector2d(0, -1)),
    SW(new Vector2d(-1, -1)),
    W(new Vector2d(-1, 0)),
    NW(new Vector2d(-1, 1));

    private static final MapDirection[] BY_INDEX;

    static {
        MapDirection[] dirs = values();
        BY_INDEX = new MapDirection[dirs.length];
        for (MapDirection d : dirs) {
            BY_INDEX[d.ordinal()] = d;
        }
    }

    public final Vector2d forwardVector;

    MapDirection(final Vector2d forwardVector) {
        this.forwardVector = forwardVector;
    }

    MapDirection rotateRight(int amount) {
        assert 0 <= amount && amount < BY_INDEX.length;
        return BY_INDEX[(ordinal() + amount) % BY_INDEX.length];
    }

    @Override
    public String toString() {
        return name();
    }
}
