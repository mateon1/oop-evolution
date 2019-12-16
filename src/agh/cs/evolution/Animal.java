package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

import java.util.Random;

public class Animal {
    private final Random rng;
    private MapDirection direction = MapDirection.N;
    private final Genome genome;

    public Animal(Random rng, Genome genome) {
        this.rng = rng;
        this.genome = genome;
    }

    public Vector2d chooseMovementVector() {
        this.direction = this.direction.rotateRight(genome.pick(rng));
        return this.direction.forwardVector;
    }
}
