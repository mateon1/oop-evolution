package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;

import java.util.*;

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
        HashMap<Vector2d, ArrayList<Animal>> pos2Animals = new HashMap<>();
        for (Animal a : animals) {
            a.tick(simParams.mapSize);
            a.energyChange(-simParams.moveEnergy);
            Vector2d new_pos = a.getPosition();
            if (!pos2Animals.containsKey(new_pos))
                pos2Animals.put(new_pos, new ArrayList<>());
            pos2Animals.get(new_pos).add(a);
        }

        // Handle each animal cell: Eat grass, mate, or die
        for (Map.Entry<Vector2d, ArrayList<Animal>> cell : pos2Animals.entrySet()) {
            Vector2d position = cell.getKey();
            ArrayList<Animal> cellAnimals = cell.getValue();
            if (cellAnimals.isEmpty()) continue;

            cellAnimals.sort(Comparator.comparingInt(Animal::getEnergy));

            // Track indices of animals with best energy
            int bestEnergy = cellAnimals.get(cellAnimals.size() - 1).getEnergy();
            int indexOfFirstBest = cellAnimals.size() - 1;
            while (indexOfFirstBest > 0 && cellAnimals.get(indexOfFirstBest - 1).getEnergy() == bestEnergy)
                indexOfFirstBest--;

            int numBestAnimals = cellAnimals.size() - indexOfFirstBest;

            // Grass eating
            if (grassField[position.x][position.y]) {
                grassField[position.x][position.y] = false;

                int spreadEnergy = simParams.plantEnergy / numBestAnimals;
                int remainder = simParams.plantEnergy % numBestAnimals;

                for (int i = 0; i < numBestAnimals; i++) {
                    int energyDelta = spreadEnergy + (numBestAnimals - i >= remainder ? 1 : 0);
                    cellAnimals.get(indexOfFirstBest + i).energyChange(energyDelta);
                }

                bestEnergy += spreadEnergy;
                if (remainder != 0) {
                    indexOfFirstBest += numBestAnimals - remainder;
                    numBestAnimals = remainder;
                    bestEnergy += 1;
                }

            }

            // Mating
            if (cellAnimals.size() < 2) continue;

            Animal a1;
            Animal a2;
            int childEnergy = 0;
            if (numBestAnimals == 1) {
                a1 = cellAnimals.get(cellAnimals.size() - 1);
                a2 = cellAnimals.get(cellAnimals.size() - 2);

                childEnergy += a1.getEnergy() / 4;
                childEnergy += a2.getEnergy() / 4;
                a1.energyChange(-a1.getEnergy() / 4);
                a2.energyChange(-a2.getEnergy() / 4);
            } else {
                int i = rng.nextInt(numBestAnimals), j = rng.nextInt(numBestAnimals - 1);
                if (j >= i) j++;
                a1 = cellAnimals.get(indexOfFirstBest + i);
                a2 = cellAnimals.get(indexOfFirstBest + j);

                assert a1.getEnergy() == bestEnergy;
                assert a2.getEnergy() == bestEnergy;

                childEnergy = bestEnergy / 4 * 2; // Not the same as bestEnergy / 2 due to rounding
                a1.energyChange(-bestEnergy / 4);
                a2.energyChange(-bestEnergy / 4);
            }
            // Do not track animals with best energy anymore, the loop is about to end.

            Animal child = new Animal(animalRng, a1.getGenes().crossover(a2.getGenes(), rng), position, childEnergy);
            animals.add(child);
        }

        // Remove dead animals
        ArrayList<Animal> liveAnimals = new ArrayList<>();
        for (Animal a : animals) {
            if (a.getEnergy() > 0)
                liveAnimals.add(a);
        }
        animals = liveAnimals;

        growGrass();
    }

    private void growGrass() {
        // TODO
    }
}
