/* *****************************************************************************
 *  Name: Biyun Wu
 *  Date: 11/05/2018
 *  Description: A statistics programm to examin the Percolation algorithm.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int sideLength; // Grid unit
    private int totalSitesNum;
    private double[] percolationRateList;
    private int trails;
    private double meanPercolationRate = 0;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("All inputs should greater than 0.");
        }
        else {
            sideLength = n;
            totalSitesNum = n * n;
            percolationRateList = new double[n];
            trails = trials;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        for (int i = 0; i < trails; i++) {
            Percolation p = new Percolation(sideLength);
            int row = getRandomIdx();
            int col = getRandomIdx();
            while (!p.percolates()) {
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
                else {
                    row = getRandomIdx();
                    col = getRandomIdx();
                }
            }
            double currPercolationRate = (double) p.numberOfOpenSites() / totalSitesNum;
            percolationRateList[i] = currPercolationRate;
            System.out.println("Percolation Rate " + (i + 1) + ": " + currPercolationRate);
        }
        double sum = 0;
        int count = 0;
        for (int k = 0; k < percolationRateList.length; k++) {
            if (percolationRateList[k] != 0) {
                sum += percolationRateList[k];
                count++;
            }
        }
        meanPercolationRate = sum / count;
        return meanPercolationRate;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (meanPercolationRate == 0) {
            mean();
        }
        double sum = 0;
        for (int i = 0; i < percolationRateList.length; i++) {
            sum += Math.pow(percolationRateList[i] - meanPercolationRate, 2);
        }
        return Math.sqrt(sum / (trails - 1));
    }

    //
    // // low  endpoint of 95% confidence interval
    // public double confidenceLo() {
    //
    // }
    //
    // // high endpoint of 95% confidence interval
    // public double confidenceHi() {
    //
    // }
    //
    private int getRandomIdx() {
        // StdRandom.uniform(a, b) return an integer of [a, b)
        return StdRandom.uniform(1, sideLength + 1);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 200);
        System.out.println("Mean: " + ps.mean());
        System.out.println("Stddev: " + ps.stddev());
    }
}
