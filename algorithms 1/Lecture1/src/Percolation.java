public class Percolation {

    private static final int VIRTUAL_NODE_COUNT = 2;
    private static final int LOWER_INDEX_BOUND = 1;
    private static final int MINIMUM_GRID_SIZE = 1;

    private final WeightedQuickUnionUF weightedQuickUnionUF;
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
        this.weightedQuickUnionUF = new WeightedQuickUnionUF((N * N)
                + VIRTUAL_NODE_COUNT);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateGridArguments(i, j);

        int index = computeGridIndex(i, j);
        if (!this.openGrid[index]) {
            this.openGrid[index] = true;

            // check if the cell can be joined to neighbours
            if (this.openGrid[getNorthIndex(index)]) {
                this.weightedQuickUnionUF.union(index, getNorthIndex(index));
            }

            if (this.openGrid[getSouthIndex(index)]) {
                this.weightedQuickUnionUF.union(index, getSouthIndex(index));
            }

            if (j > 1 && this.openGrid[getWestIndex(index)]) {
                this.weightedQuickUnionUF.union(index, getWestIndex(index));
            }

            if (j < N && this.openGrid[getEastIndex(index)]) {
                this.weightedQuickUnionUF.union(index, getEastIndex(index));
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

        return weightedQuickUnionUF.connected(computeGridIndex(i, j), 0);
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0,
                ((N * N) + VIRTUAL_NODE_COUNT) - 1);
    }
}
