package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldMap {
    protected ArrayList<Animal> animals;
    protected HashMap<Vector2d, Animal> animalHashMap;

    protected Random animalRng;

    protected WorldMap() {
        animals = new ArrayList<>();
        animalHashMap = new HashMap<>();
    }
}
