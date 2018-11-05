/* *****************************************************************************
 *  Name: Biyun Wu
 *  Date: 11/05/2018
 *  Description: Week 1 assginment for Algorithms Part I. https://www.coursera.org/learn/algorithms-part1
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int sideLength;
    private WeightedQuickUnionUF uf;
    // Record the open/close status of each site.
    private boolean[] cellsStatus;
    private int openSitesNum = 0;
    private int virtrueTopIdx;
    private int virtrueBottomIdx;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        sideLength = n;
        // The parameter is the size of the Union Finder data structure.
        uf = new WeightedQuickUnionUF(n * n + 2);
        cellsStatus = new boolean[n * n];
        // Use the second last object as the virture top site.
        virtrueTopIdx = n * n;
        // Use the last object as the virture bottom site.
        virtrueBottomIdx = n * n + 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = xyTo1D(row, col);
        if (isIndexIlegal(idx) && !cellsStatus[idx]) {
            cellsStatus[idx] = true;
            openSitesNum++;

            // Check if the site is on the first row or the last row,
            // if yes, connect it to the virtrue top site or the virtrue bottom site.
            if (row == 1) {
                uf.union(idx, virtrueTopIdx);
            }
            if (row == sideLength) {
                uf.union(idx, virtrueBottomIdx);
            }

            // Connect the site to its open neighbours.
            int[] neighbourIndices = getNeighbourSitesIndices(idx);
            for (int i : neighbourIndices) {
                if (isIndexIlegal(i) && cellsStatus[i]) {
                    uf.union(idx, i);
                }
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int idx = xyTo1D(row, col);
        if (isIndexIlegal(idx)) {
            return cellsStatus[idx];
        }
        else {
            return false;
        }
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int idx = xyTo1D(row, col);
        if (isIndexIlegal(idx)) {
            return uf.connected(idx, virtrueTopIdx);
        }
        else {
            return false;
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtrueTopIdx, virtrueBottomIdx);
    }

    // get the correspondant index of given row and column.
    private int xyTo1D(int row, int col) {
        return ((row - 1) * sideLength - 1) + col;
    }

    private boolean isIndexIlegal(int index) {
        return index >= 0 && index < cellsStatus.length;
    }

    private int[] getNeighbourSitesIndices(int index) {
        return new int[] {
                index - sideLength, index + sideLength, index - 1, index + 1
        };
    }

    // For test
    // public static void main(String[] args) {
    //     Percolation p = new Percolation(20);
    //     System.out.println(Arrays.toString(p.cellsStatus));
    //     System.out.println("length " + p.cellsStatus.length);
    //     System.out.println("Open 3, 4 ");
    //     p.open(3, 4);
    //     System.out.println("Is 4, 5 open? " + p.isOpen(4, 5));
    // }
}
