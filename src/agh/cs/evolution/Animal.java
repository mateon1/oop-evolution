package agh.cs.evolution;

public class Animal {
    private final AbstractWorldMap map;
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;

    public Animal(AbstractWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    public Animal(AbstractWorldMap map, Vector2d initialPosition) {
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
                Vector2d old_pos = this.position;
                Vector2d new_pos = this.position.add(this.direction.toUnitVector());
                if (!map.canMoveTo(new_pos))
                    return;
                this.position = new_pos;
                map.positionChanged(old_pos, new_pos);
                break;
            case BACKWARD:
                old_pos = this.position;
                new_pos = this.position.subtract(this.direction.toUnitVector());
                if (!map.canMoveTo(new_pos))
                    return;
                this.position = new_pos;
                map.positionChanged(old_pos, new_pos);
                break;
            default:
                throw new IllegalArgumentException("Invalid MoveDirection");
        }
    }
}
