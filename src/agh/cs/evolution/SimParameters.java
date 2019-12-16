package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

public class SimParameters {
    public final Vector2d mapSize;
    public final Vector2d jungleSize;
    public final int startEnergy;
    public final int plantEnergy;
    public final int moveEnergy;

    public SimParameters() {
        this(new Vector2d(100, 30), new Vector2d(10, 10), 10, 5, 1);
    }

    public SimParameters(
            Vector2d mapSize,
            Vector2d jungleSize,
            int startEnergy,
            int plantEnergy,
            int moveEnergy) {
        this.mapSize = mapSize;
        this.jungleSize = jungleSize;
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
    }
}
