package agh.cs.evolution;

import java.util.Random;

public class Genome {
    public static final int GENE_COUNT = 32;
    public static final int GENE_VALUES = 8;

    private final byte[] genes;

    public Genome(Random initializationRng) {
        genes = new byte[GENE_COUNT];

        // A buffer of genes to ensure that there always exists at least one of each gene.
        for (int i = 0; i < GENE_VALUES; i++) {
            genes[i] = (byte) i;
        }
        for (int i = GENE_VALUES; i < GENE_COUNT; i++) {
            genes[i] = (byte) initializationRng.nextInt(GENE_VALUES);
        }
    }

    private Genome(byte[] genes) {
        assert genes.length == GENE_COUNT;

        for (int i = 0; i < GENE_COUNT; i++) {
            assert 0 <= genes[i] && genes[i] < GENE_VALUES;
        }

        this.genes = genes;
    }

    public byte pick(Random rng) {
        return genes[rng.nextInt(GENE_COUNT)];
    }

    // Utility method for random number generation
    private int random_range(Random rng, int low, int high) {
        return rng.nextInt(high - low) + low;
    }

    public Genome crossover(Genome other, Random rng) {
        byte[] new_genes = new byte[GENE_COUNT];
        byte[] genes1;
        byte[] genes2;
        if (rng.nextBoolean()) {
            genes1 = this.genes;
            genes2 = other.genes;
        } else {
            genes1 = other.genes;
            genes2 = this.genes;
        }

        int j = random_range(rng, GENE_VALUES + 1, GENE_COUNT - 1);
        int k = random_range(rng, GENE_VALUES + 1, GENE_COUNT - 2); // cannot be == GENE_VALUES and == GENE_COUNT
        if (k >= j)
            k++; // ensure the numbers are distinct
        else {
            int tmp = j;
            j = k;
            k = tmp;
        }

        System.arraycopy(genes1, 0, new_genes, 0, j);
        System.arraycopy(genes2, j, new_genes, j, k - j);
        System.arraycopy(genes1, k, new_genes, k, GENE_COUNT - k);

        return new Genome(new_genes);
    }
}
