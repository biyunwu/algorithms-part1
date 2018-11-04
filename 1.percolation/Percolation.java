/* *****************************************************************************
 *  Name: Biyun Wu
 *  Date: 11/05/2018
 *  Description: Week 1 assginment for Algorithms Part I. https://www.coursera.org/learn/algorithms-part1
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    public int sideLength;
    public WeightedQuickUnionUF uf;
    // Record the open/close status of each site.
    public boolean[] cellsStatus;
    public int openSitesNum = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        sideLength = n;
        uf = new WeightedQuickUnionUF(n);
        cellsStatus = new boolean[n * n];
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = getIndex(row, col);
        if (!cellsStatus[idx]) {
            cellsStatus[idx] = true;
            openSitesNum++;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return cellsStatus[getIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // get the correspondant index of given row and column.
    public int getIndex(int row, int col) {
        return ((row - 1) * sideLength - 1) + col;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(20);
        System.out.println(Arrays.toString(p.cellsStatus));
        System.out.println("length " + p.cellsStatus.length);
        System.out.println("Open 3, 4 ");
        p.open(3, 4);
        System.out.println("Is 3, 4 open? " + p.isOpen(3, 4));
    }
}
