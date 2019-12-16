package agh.cs.evolution;

import java.util.Random;

public class Animal {
    private final Random rng;
    private MapDirection direction = MapDirection.N;

    public Animal(Random rng) {
        this.rng = rng;
    }

    public Vector2d chooseMovementVector() {
        this.direction = this.direction.rotateRight(rng.nextInt(8));

        return this.direction.forwardVector;
    }
}
