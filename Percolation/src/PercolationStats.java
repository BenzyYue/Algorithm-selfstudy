import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;

public class PercolationStats {
    private Percolation trail;
    private double[] trialData;
    private double trailMean;
    private double trailDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trialData = new double[trials];
        this.trailMean = 0;
        this.trailDev = 0;
        int trialTimes = 0;

        while (trialTimes < trials) {
            this.trail = new Percolation(n);
            while (!this.trail.percolates()) {
                this.trail.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
            }
            this.trialData[trialTimes] = (double) this.trail.numberOfOpenSites() / (double) (n * n);
            trialTimes++;
        }
        this.trailMean = StdStats.mean(trialData);
        this.trailDev = StdStats.stddev(trialData);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.trailMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.trailDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.trailMean - 1.96 * this.trailDev / Math.sqrt(this.trialData.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.trailMean + 1.96 * this.trailDev / Math.sqrt(this.trialData.length);
    }

    // test client
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + test.trailMean);
        System.out.println("stddev = " + test.trailDev);
        System.out.println("[" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
