package agh.cs.evolution;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPos, Vector2d newPos);
}
