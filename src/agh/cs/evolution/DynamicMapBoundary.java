package agh.cs.evolution;

import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Comparator.comparingInt;

public class DynamicMapBoundary implements IPositionChangeObserver {

    private SortedSet<Vector2d> xySet;
    private SortedSet<Vector2d> yxSet;

    public DynamicMapBoundary() {
        xySet = new TreeSet<>(comparingInt((Vector2d vec) -> vec.x).thenComparingInt(vec -> vec.y));
        yxSet = new TreeSet<>(comparingInt((Vector2d vec) -> vec.y).thenComparingInt(vec -> vec.x));
    }

    public void addPosition(Vector2d pos) {
        this.xySet.add(pos);
        this.yxSet.add(pos);
    }

    public Vector2d getLowerBound() {
        return xySet.first().lowerLeft(yxSet.first());
    }

    public Vector2d getUpperBound() {
        return xySet.last().upperRight(yxSet.last());
    }

    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        this.xySet.remove(oldPos);
        this.yxSet.remove(oldPos);
        this.xySet.add(newPos);
        this.yxSet.add(newPos);
    }
}
