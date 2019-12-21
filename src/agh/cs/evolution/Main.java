package agh.cs.evolution;

import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimParameters params = SimParameters.fromJson(
                "{\"mapSize\": [100, 30], \"jungleSize\": [10, 10],"
                        + " \"startEnergy\": 20, \"plantEnergy\": 25, \"moveEnergy\": 1,"
                        + " \"startAnimals\": 20, \"grassTurns\": 50}"
        );
        int seed = 0;

        WorldMap w = new WorldMap(params, seed);

        System.out.println("Growing grass for a bit");
        for (int i = 0; i < params.grassTurns; i++)
            w.tick();

        System.out.println("Creating life");
        for (int i = 0; i < params.startAnimals; i++)
            w.createAnimal();

        System.out.println("Running the world!");
        for (int i = 0; i < 100000; i++) {
            w.tick();
            if (i % 10 == 0) {
                w.visualize();
                try {
                    Thread.sleep(10, 0);
                } catch (InterruptedException ignored) {
                }
            }
        }

        System.out.println("Done!");
    }
}
