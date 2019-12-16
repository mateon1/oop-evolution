package agh.cs.evolution;

import java.util.Random;

public class Genome {
    public static final int GENE_COUNT = 32;
    public static final int GENE_VALUES = 8;

    private final byte[] genes;

    public Genome(Random initializationRng) {
        genes = new byte[GENE_COUNT];

        for (int i = 0; i < GENE_COUNT; i++) {
            genes[i] = (byte) initializationRng.nextInt(GENE_VALUES);
        }
        // FIXME: Ensure the genome is valid, i.e. has at least one of each gene
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

        int j = rng.nextInt(GENE_COUNT + 1);
        int k = rng.nextInt(GENE_COUNT + 1); // can be == 0 and == GENE_COUNT
        if (j > k) {
            int tmp = j;
            j = k;
            k = tmp;
        }

        System.arraycopy(genes1, 0, new_genes, 0, j);
        System.arraycopy(genes2, j, new_genes, j, k - j);
        System.arraycopy(genes1, k, new_genes, k, GENE_COUNT - k);

        // TODO: Check that there exists at least one of each type of gene and fix genome if not

        return new Genome(new_genes);
    }
}