package agh.cs.evolution;

import java.util.ArrayList;
import java.util.Random;

public class WorldMap {
    private boolean[][] grassField;
    private ArrayList<Animal> animals;

    private SimParameters simParams;

    private Random rng;
    private Random animalRng;

    public WorldMap(SimParameters params, long rngSeed) {
        assert params.jungleSize.precedes(params.mapSize);
        simParams = params;

        animals = new ArrayList<>();
        grassField = new boolean[params.mapSize.x][params.mapSize.y];

        rng = new Random(rngSeed);
        animalRng = new Random(rng.nextLong());
    }

    public void tick() {
        // Movement
        for (Animal a : animals) {
            a.move(simParams.mapSize);
        }
        // Eat grass
        // TODO
        // Mate
        // TODO
        // Grow grass
        // TODO
    }
}
