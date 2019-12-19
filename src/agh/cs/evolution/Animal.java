package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

import java.util.Random;

public class Animal {
    private final Random rng;
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Genome genome;

    public Animal(Random rng, Genome genome, Vector2d pos, int startEnergy) {
        this.rng = rng;
        this.genome = genome;
        this.position = pos;
        this.energy = startEnergy;
        this.direction = MapDirection.N.rotateRight(rng.nextInt(8));
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public void energyChange(int delta) {
        // delta can be positive or negative
        this.energy += delta;
    }

    public void tick(Vector2d bounds) {
        this.direction = this.direction.rotateRight(genome.pick(rng));
        this.position = this.position.add(this.direction.forwardVector).wrapBounds(bounds);
        // The world map controls eating, energy, etc.
    }

    public Genome getGenes() {
        return genome;
    }
}
