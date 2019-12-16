package agh.cs.evolution;

public enum MapDirection {
    N(0, new Vector2d(0, 1)),
    NE(1, new Vector2d(1, 1)),
    E(2, new Vector2d(1, 0)),
    SE(3, new Vector2d(1, -1)),
    S(4, new Vector2d(0, -1)),
    SW(5, new Vector2d(-1, -1)),
    W(6, new Vector2d(-1, 0)),
    NW(7, new Vector2d(-1, 1));

    private static final MapDirection[] BY_INDEX;

    static {
        MapDirection[] vals = values();
        BY_INDEX = new MapDirection[vals.length];
        for (MapDirection d : vals) {
            BY_INDEX[d.directionIndex] = d;
        }
    }

    public final Vector2d forwardVector;
    private final int directionIndex;

    MapDirection(int directionIndex, final Vector2d forwardVector) {
        this.directionIndex = directionIndex;
        this.forwardVector = forwardVector;
    }

    MapDirection rotateRight(int amount) {
        assert 0 <= amount && amount < BY_INDEX.length;
        return BY_INDEX[(this.directionIndex + amount) % BY_INDEX.length];
    }

    @Override
    public String toString() {
        return name();
    }
}
