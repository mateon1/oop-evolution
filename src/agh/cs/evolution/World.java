package agh.cs.evolution;

public class World {
    public static void main(String[] args) {
        // f b r l f f r r f f f f f f f f
        try {
            MoveDirection[] directions = OptionsParser.parse(args);
            GrassField map = new GrassField(10);
            map.place(new Animal(map));
            map.place(new Animal(map, new Vector2d(3, 4)));
            map.run(directions, true);
        } catch (IllegalArgumentException ex) {
            System.err.println("Encountered a fatal error while running the program.");
            ex.printStackTrace(System.err);
        }
    }
}
