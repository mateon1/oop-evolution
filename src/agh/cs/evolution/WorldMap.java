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

    public void createAnimal() {
        Genome genes = new Genome(rng);
        Vector2d pos = new Vector2d(rng.nextInt(simParams.mapSize.x), rng.nextInt(simParams.mapSize.y));
        createAnimal(pos, genes);
    }

    public void createAnimal(Vector2d position, Genome genes) {
        animals.add(new Animal(animalRng, genes, position, simParams.startEnergy));
    }

    private HashMap<Vector2d, ArrayList<Animal>> getAnimalMap() {
        HashMap<Vector2d, ArrayList<Animal>> animalCells = new HashMap<>();
        for (Animal a : animals) {
            Vector2d pos = a.getPosition();
            if (!animalCells.containsKey(pos))
                animalCells.put(pos, new ArrayList<>());
            animalCells.get(pos).add(a);
        }
        for (Map.Entry<Vector2d, ArrayList<Animal>> cell : animalCells.entrySet()) {
            cell.getValue().sort(Comparator.comparingInt(Animal::getEnergy));
        }
        return animalCells;
    }

    private int countGrass() {
        int grass = 0;
        for (int x = 0; x < simParams.mapSize.x; x++) {
            for (int y = 0; y < simParams.mapSize.y; y++) {
                if (grassField[x][y])
                    grass++;
            }
        }
        return grass;
    }

    public void tick() {
        // Movement
        for (Animal a : animals) {
            a.tick(simParams.mapSize);
            a.energyChange(-simParams.moveEnergy);
        }

        // Handle each animal cell: Eat grass, mate, or die
        HashMap<Vector2d, ArrayList<Animal>> animalMap = getAnimalMap();
        for (Map.Entry<Vector2d, ArrayList<Animal>> cell : animalMap.entrySet()) {
            Vector2d position = cell.getKey();
            ArrayList<Animal> cellAnimals = cell.getValue();

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

            // Choose parent animals
            Animal a1 = cellAnimals.get(cellAnimals.size() - 1);
            Animal a2 = cellAnimals.get(cellAnimals.size() - 2);
            if (numBestAnimals > 2) {
                int i = rng.nextInt(numBestAnimals), j = rng.nextInt(numBestAnimals - 1);
                if (j >= i) j++; // ensure indices are distinct
                a1 = cellAnimals.get(indexOfFirstBest + i);
                a2 = cellAnimals.get(indexOfFirstBest + j);
                assert a1.getEnergy() == bestEnergy;
                assert a2.getEnergy() == bestEnergy;
            }

            // Don't mate if either animal has insufficient energy
            if (a1.getEnergy() < simParams.startEnergy / 2) continue;
            if (a2.getEnergy() < simParams.startEnergy / 2) continue;

            int childEnergy = 0;

            int energyDelta = a1.getEnergy() / 4;
            a1.energyChange(-energyDelta);
            childEnergy += energyDelta;

            energyDelta = a2.getEnergy() / 4;
            a2.energyChange(-energyDelta);
            childEnergy += energyDelta;

            ArrayList<Vector2d> neighboringPositions = new ArrayList<>();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    Vector2d pos = position.add(new Vector2d(x, y)).wrapBounds(simParams.mapSize);
                    if (!animalMap.containsKey(pos))
                        neighboringPositions.add(pos);
                }
            }

            Vector2d childPosition;
            if (neighboringPositions.size() == 0) {
                childPosition = position;
            } else {
                childPosition = neighboringPositions.get(rng.nextInt(neighboringPositions.size()));
            }

            Animal child = new Animal(
                    animalRng,
                    a1.getGenes().crossover(a2.getGenes(), rng),
                    childPosition,
                    childEnergy);
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
        boolean grownInJungle = false;
        boolean grownInPlains = false;

        Set<Vector2d> animalSpots = getAnimalMap().keySet();

        // 16 initial random growing attempts
        for (int i = 0; i < 16; i++) {
            int width = simParams.mapSize.x;
            int height = simParams.mapSize.y;
            if (grownInPlains && !grownInJungle) {
                width = simParams.jungleSize.x;
                height = simParams.jungleSize.y;
            }

            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            Vector2d pos = new Vector2d(x, y);
            if (grassField[x][y] || animalSpots.contains(pos))
                continue;

            if (pos.precedes(simParams.jungleSize)) {
                if (!grownInJungle) {
                    grassField[x][y] = true;
                    grownInJungle = true;
                }
            } else {
                if (!grownInPlains) {
                    grassField[x][y] = true;
                    grownInPlains = true;
                }
            }
        }

        if (grownInJungle && grownInPlains) return;

        // Go through all free spots to generate next grass spot
        ArrayList<Vector2d> freeCells = new ArrayList<>();
        for (int x = 0; x < simParams.mapSize.x; x++) {
            for (int y = 0; y < simParams.mapSize.y; y++) {
                if (!grassField[x][y] && !animalSpots.contains(new Vector2d(x, y))) {
                    freeCells.add(new Vector2d(x, y));
                }
            }
        }
        Collections.shuffle(freeCells, rng);

        for (Vector2d pos : freeCells) {
            if (pos.precedes(simParams.jungleSize)) {
                if (!grownInJungle) {
                    grownInJungle = true;
                    grassField[pos.x][pos.y] = true;
                }
            } else {
                if (!grownInPlains) {
                    grownInPlains = true;
                    grassField[pos.x][pos.y] = true;
                }
            }

            if (grownInJungle && grownInPlains) return;
        }
    }

    void visualize() {
        // Return to top of screen, should prevent text flickering
        System.out.println("\u001b[H");

        System.out.print('+');
        for (int i = 0; i < simParams.mapSize.x; i++)
            System.out.print('-');
        System.out.println('+');

        HashMap<Vector2d, ArrayList<Animal>> animalMap = getAnimalMap();

        for (int y = 0; y < simParams.mapSize.y; y++) {
            System.out.print('|');
            for (int x = 0; x < simParams.mapSize.x; x++) {
                char c = grassField[x][y] ? ',' : ' ';
                Vector2d pos = new Vector2d(x, y);
                if (animalMap.containsKey(pos)) {
                    int count = animalMap.get(pos).size();
                    if (count < 10) c = " o23456789".charAt(count);
                    else c = '#';
                }
                System.out.print(c);
            }
            System.out.println('|');
        }

        System.out.print('+');
        for (int i = 0; i < simParams.mapSize.x; i++)
            System.out.print('-');
        System.out.println('+');

        int totalEnergy = this.animals.stream().map(Animal::getEnergy).reduce(0, Integer::sum);

        System.out.println("=== STATISTICS ===");
        System.out.printf("Animal count:   %4d    \n", this.animals.size());
        System.out.printf("Average energy: %7.2f \n", (double) totalEnergy / this.animals.size());
        System.out.printf("Grass count:    %4d    \n", countGrass());
    }
}
