/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double COFIDENCE_95 = 1.96;
    private double[] x;
    private double xMean;
    private double s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Illeagal Arguements");
        }
        x = new double[trials];
        experiment(n, trials);
        xMean = StdStats.mean(x);
        s = StdStats.stddev(x);
    }

    private void experiment(int n, int trials) {
        Percolation percolation;
        boolean[] isEmptyLine;
        int numOfLine;

        for (int i = 0; i < trials; i++) {
            isEmptyLine = new boolean[n];
            numOfLine = 0;
            percolation = new Percolation(n);
            while (true) {
                int posX = StdRandom.uniform(n) + 1;
                int posY = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(posX, posY)) {
                    percolation.open(posX, posY);
                    x[i]++;
                    if (!isEmptyLine[posX - 1]) {
                        isEmptyLine[posX - 1] = true;
                        numOfLine++;
                    }
                    if (numOfLine == n) {
                        if (percolation.percolates()) {
                            break;
                        }
                    }
                }
            }
            x[i] /= n * n;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return xMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (x.length == 1) {
            return Double.NaN;
        }
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return xMean - (s * COFIDENCE_95 / Math.sqrt(x.length));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return xMean + (s * COFIDENCE_95 / Math.sqrt(x.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Usage: PercolationStats N T");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.print("mean                     = ");
        StdOut.println(percStats.mean());
        StdOut.print("stddev                     = ");
        StdOut.println(percStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percStats.confidenceLo(),
                      percStats.confidenceHi());

    }
}
