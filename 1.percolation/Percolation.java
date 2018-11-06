/* *****************************************************************************
 *  Name: Biyun Wu
 *  Date: 11/05/2018
 *  Description: Week 1 assginment for Algorithms Part I. https://www.coursera.org/learn/algorithms-part1
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int sideLength;
    private int totalSitesNum;
    private WeightedQuickUnionUF uf;
    // Record the open/close status of each site.
    private boolean[] cellsStatus;
    private int openSitesNum = 0;
    private int virtrueTopIdx;
    private int virtrueBottomIdx;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        sideLength = n;
        totalSitesNum = n * n;
        // The parameter is the size of the Union Finder data structure.
        uf = new WeightedQuickUnionUF(totalSitesNum + 2);
        cellsStatus = new boolean[totalSitesNum];
        // Use the second last object as the virture top site.
        virtrueTopIdx = totalSitesNum;
        // Use the last object as the virture bottom site.
        virtrueBottomIdx = totalSitesNum + 1;
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
            int[] neighbourIndices = getNeighbourSitesIndices(row, col);

            for (int i = 0; i < neighbourIndices.length; i++) {
                int currIdx = neighbourIndices[i];
                if (isIndexIlegal(currIdx) && cellsStatus[currIdx] && !uf.connected(currIdx, idx)) {
                    uf.union(currIdx, idx);
                }
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int idx = xyTo1D(row, col);
        if (!isIndexIlegal(idx)) {
            checkArguments(new int[] { idx });
        }
        return cellsStatus[idx];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int idx = xyTo1D(row, col);
        if (!isIndexIlegal(idx)) {
            checkArguments(new int[] { idx });
        }
        return uf.connected(idx, virtrueTopIdx);
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
        checkArguments(new int[] { row, col });
        return ((row - 1) * sideLength - 1) + col;
    }

    private boolean isIndexIlegal(int index) {
        return index >= 0 && index < totalSitesNum;
    }

    private int[] getNeighbourSitesIndices(int row, int col) {
        int index = xyTo1D(row, col);
        int above = index - sideLength, below = index + sideLength, left = index - 1, right = index
                + 1;
        // if (row == 1 && col == 1) {
        //     return new int[] { below, right };
        // }
        // else if (row == 1 && col == sideLength) {
        //     return new int[] { below, left };
        // }
        // else if (row == sideLength && col == 1) {
        //     return new int[] { above, right };
        // }
        // else if (row == sideLength && col == sideLength) {
        //     return new int[] { above, left };
        // }
        if (col == 1) {
            // In the first column, no left sites.
            return new int[] { above, below, right };
        }
        else if (col == sideLength) {
            // In the last column, no right sites.
            return new int[] { above, below, left };
        }
        else if (row == 1) {
            // In the first row, no above site.
            return new int[] { below, left, right };
        }
        else if (row == sideLength) {
            // In the last row, no below site.
            return new int[] { above, left, right };
        }
        else {
            return new int[] { above, below, left, right };
        }
    }

    private void checkArguments(int[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] <= 0) {
                throw new java.lang.IllegalArgumentException("Illegal arguments!");
            }
        }
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
