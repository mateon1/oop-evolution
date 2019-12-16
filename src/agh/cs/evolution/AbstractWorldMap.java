package agh.cs.evolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected ArrayList<Animal> animals;
    protected HashMap<Vector2d, Animal> animalHashMap;

    protected AbstractWorldMap() {
        animals = new ArrayList<>();
        animalHashMap = new HashMap<>();
    }

    protected Iterator<Vector2d> getObjectPositions() {
        return animalHashMap.keySet().iterator();
    }

    @Override
    public String toString() {
        // XXX: What about the empty (grass and animal) case?

        Iterator<Vector2d> positions = getObjectPositions();

        assert positions.hasNext();
        Vector2d lowerBound; // FIXME: How should this be handled?
        Vector2d upperBound;
        lowerBound = upperBound = positions.next();

        // Cannot use positions.forEachRemaining as I need to modify local variables
        // And the Java type system is not powerful enough to return a (min,max) tuple easily.
        // I also investigated Stream::reduce, but to no effect.
        while (positions.hasNext()) {
            Vector2d pos = positions.next();
            lowerBound = lowerBound.lowerLeft(pos);
            upperBound = upperBound.upperRight(pos);
        }

        MapVisualizer mv = new MapVisualizer(this);
        return mv.draw(lowerBound, upperBound);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animalHashMap.containsKey(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!this.canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException("Cannot add animal at position "
                    + animal.getPosition().toString() + ", because the position is invalid.");
        animals.add(animal);
        animalHashMap.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public void run(MoveDirection[] directions) {
        run(directions, false);
    }

    public void run(MoveDirection[] directions, boolean visualize) {
        assert !animals.isEmpty();
        int curAnimal = 0;
        for (MoveDirection direction : directions) {
            if (visualize)
                System.out.println(this.toString());
            animals.get(curAnimal).move(direction);
            if (visualize)
                System.out.println("--- moving: " + direction.toString() + " ---");
            curAnimal = (curAnimal + 1) % animals.size();
        }
        if (visualize)
            System.out.println(this.toString());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animalHashMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        System.out.println("pos change: " + oldPos.toString() + " -> " + newPos.toString());
        animalHashMap.put(newPos, animalHashMap.remove(oldPos));
    }
}
