/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] opened;
    private boolean percolated;
    private int numOfOpenSites = 0;
    private final WeightedQuickUnionUF hasBackwash;
    private final WeightedQuickUnionUF antiBackwash;
    private final int len;
    private int virtualTop = 0;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        len = n;
        int numOfSites = len * len + 2;
        virtualBottom = numOfSites - 1;
        opened = new boolean[numOfSites - 2];
        hasBackwash = new WeightedQuickUnionUF(numOfSites);
        antiBackwash = new WeightedQuickUnionUF(numOfSites - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        legal(row, col);
        // open site (row, col) if it is not open already
        if (isOpen(row, col)) {
            return;
        }

        numOfOpenSites++;

        int currentIndex = xyTo1D(row, col);

        opened[currentIndex] = true;

        if (row == 1) {
            hasBackwash.union(virtualTop, currentIndex);
            antiBackwash.union(virtualTop, currentIndex);
        }

        if (row == len) {
            hasBackwash.union(virtualBottom, currentIndex);
        }

        int[] dx = { 1, -1, 0, 0 };
        int[] dy = { 0, 0, 1, -1 };
        for (int i = 0; i < 4; i++) {
            int adjacentX = row + dx[i];
            int adjacentY = col + dy[i];
            if (valid(adjacentX, adjacentY) && isOpen(adjacentX, adjacentY)) {
                int adjacentIndex = xyTo1D(adjacentX, adjacentY);
                hasBackwash.union(adjacentIndex, currentIndex);
                antiBackwash.union(adjacentIndex, currentIndex);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        legal(row, col);
        return opened[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        legal(row, col);
        return isOpen(row, col) && (antiBackwash.find(virtualTop) == antiBackwash
                .find(xyTo1D(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;

    }

    // does the system percolate?
    public boolean percolates() {
        if (percolated) {
            return true;
        }
        if (hasBackwash.find(virtualBottom) == hasBackwash.find(virtualTop)) {
            percolated = true;
        }
        return percolated;

    }

    private boolean valid(int row, int col) {
        return row >= 1 && row <= len && col >= 1 && col <= len;
    }

    private void legal(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IllegalArgumentException();
        }
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * len + col - 1;
    }


}
