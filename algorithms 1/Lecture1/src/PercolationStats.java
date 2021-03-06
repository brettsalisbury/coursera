public class PercolationStats {

    private final double[] results;
    private final int T;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        this.T = T;

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        results = new double[T];
        double arraySize = N * N;

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);

            int k = -1;
            int j = -1;
            int count = 0;
            do {
                k = StdRandom.uniform(N) + 1;
                j = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(k, j)) {
                    percolation.open(k, j);
                    count++;
                }
            } while (!percolation.percolates());

            results[i] = count / arraySize;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        if (T < 30) {
            return mean() - ((1.96 * Math.sqrt(this.stddev())) / T);
        } else {
            return mean()
                    - ((1.96 * Math.sqrt(this.stddev())) / (Math.sqrt(T)));
        }
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        if (T < 30) {
            return mean() + ((1.96 * Math.sqrt(this.stddev())) / T);
        } else {
            return mean()
                    + ((1.96 * Math.sqrt(this.stddev())) / (Math.sqrt(T)));
        }
    }

    // test client, described below
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Invalid arguments: "
                    + "command line is PercolationStats <N> <T>");
            return;
        }

        PercolationStats percolationStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean\t\t\t\t" + percolationStats.mean());
        StdOut.println("stddev\t\t\t\t" + percolationStats.stddev());
        StdOut.println("95% confidence interval\t"
                + percolationStats.confidenceLo() + ", "
                + percolationStats.confidenceHi());
    }
}
