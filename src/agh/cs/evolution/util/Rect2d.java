package agh.cs.evolution.util;

public class Rect2d {
    public final Vector2d low;
    public final Vector2d high;

    public Rect2d(Vector2d lowerLeft, Vector2d upperRight) {
        assert lowerLeft.precedes(upperRight);
        this.low = lowerLeft;
        this.high = upperRight;
    }

    public Rect2d(int x1, int y1, int x2, int y2) {
        this(new Vector2d(x1, y1), new Vector2d(x2, y2));
    }

    public Vector2d getSize() {
        return high.subtract(low);
    }
}
