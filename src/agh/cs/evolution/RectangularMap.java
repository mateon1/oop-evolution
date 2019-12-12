package agh.cs.evolution;

public class RectangularMap extends AbstractWorldMap {
    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private final Vector2d upperRight;

    public RectangularMap(int width, int height) {
        super();
        assert width > 0 && height > 0;
        upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(LOWER_LEFT) &&
                position.precedes(upperRight) &&
                super.canMoveTo(position);
    }
}
