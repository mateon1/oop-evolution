package agh.cs.evolution;

public enum MapDirection {
    NORTH, EAST, SOUTH, WEST;

    @Override
    public String toString() {
        switch (this) {
            case NORTH:
                return "Północ";
            case EAST:
                return "Wschód";
            case SOUTH:
                return "Południe";
            case WEST:
                return "Zachód";
            default:
                throw new IllegalStateException("Invalid MapDirection enum value.");
        }
    }

    public char getDirectionArrow() {
        switch (this) {
            case NORTH:
                return '^';
            case EAST:
                return '>';
            case SOUTH:
                return 'v';
            case WEST:
                return '<';
            default:
                throw new IllegalStateException("Invalid MapDirection enum value.");
        }
    }

    public MapDirection next() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Invalid MapDirection enum value.");
        }
    }

    public MapDirection previous() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Invalid MapDirection enum value.");
        }
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTH:
                return new Vector2d(0, -1);
            case WEST:
                return new Vector2d(-1, 0);
            default:
                throw new IllegalStateException("Invalid MapDirection enum value.");
        }
    }
}
