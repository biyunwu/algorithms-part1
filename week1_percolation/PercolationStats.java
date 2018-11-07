/* *****************************************************************************
 *  Name: Biyun Wu
 *  Date: 11/05/2018
 *  Description: A statistics programm to examin the Percolation algorithm. http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int sideLength; // Grid unit
    private final int totalSitesNum;
    private final int trialsNum;
    private final double[] percolationRateList;
    private final double meanPercolationRate;
    private final double standardDeviation;
    private final double confidence95Lo;
    private final double confidence95Hi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("All inputs should greater than 0.");
        }
        else {
            sideLength = n;
            totalSitesNum = n * n;
            trialsNum = trials;
            percolationRateList = new double[trials];
        }

        meanPercolationRate = calculateAveragePercolationThreshold();
        standardDeviation = StdStats.stddev(percolationRateList);
        double confidence95PercentCoefficient = 1.96;
        confidence95Lo = meanPercolationRate - (confidence95PercentCoefficient * standardDeviation
                / Math
                .sqrt(trialsNum));
        confidence95Hi = meanPercolationRate + (confidence95PercentCoefficient * standardDeviation
                / Math
                .sqrt(trialsNum));
    }

    private double calculateAveragePercolationThreshold() {
        for (int i = 0; i < trialsNum; i++) {
            Percolation p = new Percolation(sideLength);
            int row = getRandomIdx();
            int col = getRandomIdx();
            while (!p.percolates()) {
                p.open(row, col);
                row = getRandomIdx();
                col = getRandomIdx();
            }
            double currPercolationRate = (double) p.numberOfOpenSites() / totalSitesNum;
            percolationRateList[i] = currPercolationRate;
        }
        return StdStats.mean(percolationRateList);
    }

    private int getRandomIdx() {
        // StdRandom.uniform(a, b) return an integer of [a, b)
        return StdRandom.uniform(1, sideLength + 1);
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanPercolationRate;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return standardDeviation;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidence95Lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidence95Hi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[0]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("Mean                    = " + ps.mean());
        System.out.println("Standard Deviation      = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
