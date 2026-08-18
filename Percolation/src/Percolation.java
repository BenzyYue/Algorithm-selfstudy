import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Site[][] grid;
    private int size;
    private int openSites;
    private int visualBottom;
    private int visualTop;
    private WeightedQuickUnionUF openUF;
    private WeightedQuickUnionUF percolateUF;

    // private class that used to build a grid
    private static class Site {
        private int row;
        private int col;
        private int number;
        private boolean isOpen;

        public Site(int row, int col, int size) {
            this.row = row;
            this.col = col;
            this.number = ((this.row - 1) * size) + this.col;
            this.isOpen = false;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n == 0) {
            throw new IllegalArgumentException();
        } else if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.visualBottom = n * n + 1;
        this.visualTop = 0;
        this.openSites = 0;
        this.openUF = new WeightedQuickUnionUF(n * n + 2);
        this.percolateUF = new WeightedQuickUnionUF(n * n + 2);
        this.grid = new Site[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.grid[i][j] = new Site(i + 1, j + 1, this.size);
                if (i == 0) {
                    this.percolateUF.union(this.grid[i][j].number, this.visualTop);
                    this.openUF.union(this.grid[i][j].number, this.visualTop);
                }

                if (i == this.size - 1) {
                    this.percolateUF.union(this.grid[i][j].number, this.visualBottom);
                }
            }
        }
    }

    // opens the site (row, col) if it's not open already
    // 1. if this site is not open:
    //     1.1 check if its neighbor is in the grid:
    //         1.1.1 if it is:
    //         1.1.2 if it is not:
    public void open(int row, int col) {
        if (!this.isInTheGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        if (!this.isOpen(row, col)) {
            this.grid[row - 1][col - 1].isOpen = true;
            this.openSites++;
            if (this.isInTheGrid(row - 1, col)) {
                if (this.isOpen(row - 1, col) && this.isFull(row - 1, col)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 2][col - 1].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 2][col - 1].number);
                } else if (this.isOpen(row - 1, col)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 2][col - 1].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 2][col - 1].number);
                }
            }
            if (this.isInTheGrid(row + 1, col)) {
                if (this.isOpen(row + 1, col) && this.isFull(row + 1, col)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row][col - 1].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row][col - 1].number);
                } else if (this.isOpen(row + 1, col)){
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row][col - 1].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row][col - 1].number);
                }
            }
            if (this.isInTheGrid(row, col - 1)) {
                if (this.isOpen(row, col - 1) && this.isFull(row, col - 1)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col - 2].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col - 2].number);
                } else if (this.isOpen(row, col - 1)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col - 2].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col - 2].number);
                }
            }
            if (this.isInTheGrid(row, col + 1)) {
                if (this.isOpen(row, col + 1) && this.isFull(row, col + 1)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col].number);
                } else if (this.isOpen(row, col + 1)) {
                    this.openUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col].number);
                    this.percolateUF.union(this.grid[row - 1][col - 1].number, this.grid[row - 1][col].number);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!this.isInTheGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return this.grid[row - 1][col - 1].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!this.isInTheGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return this.openUF.connected(visualTop, this.grid[row - 1][col - 1].number) && this.isOpen(row, col);
    }

    // return the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (this.size == 1) {
            return this.percolateUF.connected(this.visualTop, this.visualBottom) && this.isOpen(1, 1);
        }
        return this.percolateUF.connected(this.visualTop, this.visualBottom);
    }

    // check if a site is in the grid
    private boolean isInTheGrid(int row, int col) {
        return (row > 0 && row <= this.size) && (col > 0 && col <= this.size);
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(1);
        test.open(1,1);
        System.out.println(test.isFull(1,1));
        System.out.println(test.percolates());
    }
}
