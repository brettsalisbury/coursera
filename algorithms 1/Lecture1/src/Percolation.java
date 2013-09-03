public class Percolation {

    private static final int VIRTUAL_NODE_COUNT = 2;
    private static final int LOWER_INDEX_BOUND = 1;
    private static final int MINIMUM_GRID_SIZE = 1;

    private final WeightedQuickUnionUF weightedQuickUnionUFPercolation;
    private final WeightedQuickUnionUF weightedQuickUnionUFFull;
    private final int N;
    private final boolean[] openGrid;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N < MINIMUM_GRID_SIZE) {
            throw new IndexOutOfBoundsException();
        }

        int size = (N * N) + VIRTUAL_NODE_COUNT;
        this.openGrid = new boolean[size];

        for (int i = 0; i < size; i++) {
            if (i == 0 || i == size - 1) {
                this.openGrid[i] = true;
            } else {
                this.openGrid[i] = false;
            }
        }

        this.N = N;
        this.weightedQuickUnionUFPercolation = new WeightedQuickUnionUF((N * N)
                + VIRTUAL_NODE_COUNT);
        this.weightedQuickUnionUFFull = new WeightedQuickUnionUF((N * N)
                + VIRTUAL_NODE_COUNT - 1);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateGridArguments(i, j);

        int index = computeGridIndex(i, j);
        if (!this.openGrid[index]) {
            this.openGrid[index] = true;

            // check if the cell can be joined to neighbours
            int northIndex = getNorthIndex(index);
            if (this.openGrid[northIndex]) {
                this.weightedQuickUnionUFPercolation.union(index, northIndex);
                this.weightedQuickUnionUFFull.union(index, northIndex);
            }

            // Need to differentiate between perculate and full unions here
            int southIndex = getSouthIndex(index);
            if (this.openGrid[southIndex]) {
                this.weightedQuickUnionUFPercolation.union(index, southIndex);
                if (southIndex != (N * N) + VIRTUAL_NODE_COUNT - 1) {
                    this.weightedQuickUnionUFFull.union(index, southIndex);
                }
            }

            if (j > 1) {
                int westIndex = getWestIndex(index);
                if (this.openGrid[westIndex]) {
                    this.weightedQuickUnionUFPercolation
                            .union(index, westIndex);
                    this.weightedQuickUnionUFFull.union(index, westIndex);
                }
            }

            if (j < N) {
                int eastIndex = getEastIndex(index);
                if (this.openGrid[eastIndex]) {
                    this.weightedQuickUnionUFPercolation
                            .union(index, eastIndex);
                    this.weightedQuickUnionUFFull.union(index, eastIndex);
                }
            }
        }
    }

    private int getEastIndex(int index) {
        if (index % N == 0) {
            throw new IllegalArgumentException();
        }
        return index + 1;
    }

    private int getWestIndex(int index) {
        if (index == 1) {
            throw new IllegalArgumentException();
        }
        return index - 1;
    }

    private int getSouthIndex(int index) {
        if (((N * N) - N) < index) {
            return ((N * N) + VIRTUAL_NODE_COUNT - 1);
        } else {
            return index + N;
        }

    }

    private int getNorthIndex(int index) {
        if (index <= N) {
            return 0;
        } else {
            return index - N;
        }
    }

    private void validateGridArguments(int i, int j) {
        if (i < LOWER_INDEX_BOUND || j < LOWER_INDEX_BOUND || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int computeGridIndex(int i, int j) {
        return ((i - 1) * N) + j;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateGridArguments(i, j);
        return this.openGrid[computeGridIndex(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateGridArguments(i, j);

        return weightedQuickUnionUFFull.connected(computeGridIndex(i, j), 0);
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUFPercolation.connected(0,
                ((N * N) + VIRTUAL_NODE_COUNT) - 1);
    }
}
