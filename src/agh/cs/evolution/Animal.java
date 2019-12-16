package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

import java.util.Random;

public class Animal {
    private final Random rng;
    private MapDirection direction = MapDirection.N;
    private Vector2d position;
    private final Genome genome;

    public Animal(Random rng, Genome genome, Vector2d pos) {
        this.rng = rng;
        this.genome = genome;
        this.position = pos;
    }

    public void move(Vector2d bounds) {
        this.direction = this.direction.rotateRight(genome.pick(rng));
        this.position = this.position.add(this.direction.forwardVector).wrapBounds(bounds);
    }
}
