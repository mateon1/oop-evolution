package agh.cs.evolution;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimParameters params;
        if (args.length == 0) {
            params = SimParameters.fromJson(
                    "{\"mapSize\": [100, 30], \"jungleSize\": [10, 10],"
                            + " \"startEnergy\": 50, \"plantEnergy\": 25, \"moveEnergy\": 1,"
                            + " \"startAnimals\": 25, \"grassTurns\": 50}"
            );
        } else {
            try {
                String config = new String(Files.readAllBytes(Paths.get(args[0])));
                params = SimParameters.fromJson(config);
            } catch (IOException e) {
                System.err.println("Could not read config file (" + args[0] + ")");
                e.printStackTrace();
                System.exit(1);
                // javac doesn't know that System.exit doesn't return, so
                // doesn't know that params is always set without this
                return;
            }
        }
        int seed = 0;

        System.out.println("Using seed: " + seed);
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
            w.visualize();
            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException ignored) {
            }
        }

        System.out.println("Done!");
    }
}
