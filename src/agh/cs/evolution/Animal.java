package agh.cs.evolution;

import java.util.HashSet;

public class Animal {
    private final IWorldMap map;
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private HashSet<IPositionChangeObserver> observers = new HashSet<>();

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "" + direction.getDirectionArrow();
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case FORWARD:
                Vector2d new_pos = this.position.add(this.direction.toUnitVector());
                if (!map.canMoveTo(new_pos))
                    return;
                observers.forEach(obs -> obs.positionChanged(this.position, new_pos));
                this.position = new_pos;
                break;
            case BACKWARD:
                new_pos = this.position.subtract(this.direction.toUnitVector());
                if (!map.canMoveTo(new_pos))
                    return;
                observers.forEach(obs -> obs.positionChanged(this.position, new_pos));
                this.position = new_pos;
                break;
            default:
                throw new IllegalArgumentException("Invalid MoveDirection");
        }
    }

    public void addObserver(IPositionChangeObserver obs) {
        if (!this.observers.add(obs))
            throw new IllegalStateException("Cannot add observer for Animal as it is already registered.");
    }

    public void removeObserver(IPositionChangeObserver obs) {
        if (!this.observers.remove(obs))
            throw new IllegalStateException("Cannot remove observer from Animal as it is not registered.");
    }
}
