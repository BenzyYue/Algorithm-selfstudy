import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class PercolationStats {
    double mean;
    double standardDeviation;
    double experimentTime;
    double[] experimentData;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.mean = 0;
        this.standardDeviation = 0;
        this.experimentTime = 0;
        this.experimentData = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation experiment = new Percolation(n);
            while (!experiment.percolates()) {
                experiment.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
            }
            experimentData[i] = (double) (experiment.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        this.mean = StdStats.mean(experimentData);
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        this.standardDeviation = StdStats.stddev(experimentData);
        return this.standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return (this.mean - 1.96 * this.standardDeviation) / experimentTime;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return (this.mean + 1.96 * this.standardDeviation) / experimentTime;
    }

    // test client
    public static void main(String[] args){}
}
