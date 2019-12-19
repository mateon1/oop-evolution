package agh.cs.evolution.util;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(this.x);
        sb.append(',');
        sb.append(this.y);
        sb.append(')');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (super.equals(obj)) return true;
        if (!(obj instanceof Vector2d)) return false;
        Vector2d other = (Vector2d) obj;
        return (this.x == other.x) && (this.y == other.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d wrapBounds(Vector2d upperRight) {
        return new Vector2d(
                (this.x % upperRight.x + upperRight.x) % upperRight.x,
                (this.y % upperRight.y + upperRight.y) % upperRight.y);
    }

    public Vector2d wrapBounds(Vector2d lowerLeft, Vector2d upperRight) {
        return this.subtract(lowerLeft).wrapBounds(upperRight.subtract(lowerLeft)).add(lowerLeft);
    }
}
