package agh.cs.evolution;

public class Main {
    public static void main(String[] args) {
        SimParameters params = new SimParameters();
        int seed = 0;

        WorldMap w = new WorldMap(params, seed);

        System.out.println("Growing grass for a bit");
        for (int i = 0; i < 50; i++)
            w.tick();

        System.out.println("Creating life");
        for (int i = 0; i < 20; i++)
            w.createAnimal();

        System.out.println("Running the world!");
        for (int i = 0; i < 100000; i++)
            w.tick();

        System.out.println("Done!");
    }
}
