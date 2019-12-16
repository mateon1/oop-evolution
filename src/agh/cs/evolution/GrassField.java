package agh.cs.evolution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private HashMap<Vector2d, Grass> grassMap = new HashMap<>();

    private int grassBound;
    private Random grassRand = new Random(); // XXX: What seed?

    public GrassField(int grassCount) {
        super();
        assert grassCount >= 0; // XXX: Does ==0 make sense?

        grassBound = (int) Math.ceil(Math.sqrt(10 * grassCount));

        while (grassMap.size() < grassCount)
            generateGrass();
    }

    private void generateGrass() {
        boolean redo;
        do {
            redo = false;

            int x = grassRand.nextInt(grassBound);
            int y = grassRand.nextInt(grassBound);
            Vector2d pos = new Vector2d(x, y);
            if (!this.isOccupied(pos)) {
                redo = (grassMap.put(pos, new Grass()) != null);
            }
        } while (redo);
        // OooOOooo... - possibly an infinite loop in case of bad randomness!
    }

    @Override
    protected Iterator<Vector2d> getObjectPositions() {
        return Stream.concat(
                grassMap.keySet().stream(),
                animalHashMap.keySet().stream()).iterator();
    }

    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        super.positionChanged(oldPos, newPos);
        if (grassMap.remove(newPos) != null) // Remove grass if present
            generateGrass();
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object out = super.objectAt(position);
        if (out != null) return out;
        return grassMap.get(position);
    }
}
