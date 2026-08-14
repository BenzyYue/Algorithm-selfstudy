import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    // private class that used to build a grid
    private static class site {
        public boolean isOpen;
        public boolean isFull;
        public int row;
        public int column;
        public int number;

        public site(int row, int col) {
            this.isFull = false;
            this.isOpen = false;
            this.row = row;
            this.column = col;
            this.number = row * col;
        }
    }

    private site[][] gird;
    private int numberOfOpenSites;
    private int size;
    private WeightedQuickUnionUF openUF;
    private WeightedQuickUnionUF percolateUF;
    private int visualTop;
    private int visualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.size = n;
        this.gird = new site[n][n];
        this.numberOfOpenSites = 0;
        this.openUF = new WeightedQuickUnionUF(n * n + 2);
        this.percolateUF = new WeightedQuickUnionUF(n * n + 2);
        this.visualTop = n * n + 1;
        this.visualBottom = n * n + 2;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                this.gird[i][j].row = i + 1;
                this.gird[i][j].column = j + 1;
                this.gird[i][j].isOpen = false;
                this.gird[i][j].isFull = false;
            }
        }

        for (int i = 0; i < n; i++){
            openUF.union(this.gird[0][i].number, visualTop);
            percolateUF.union(this.gird[0][i].number, visualTop);
            openUF.union(this.gird[n - 1][i].number, visualBottom);
            percolateUF.union(this.gird[n - 1][i].number, visualBottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (row <= 0 || col <= 0){
            throw new IllegalArgumentException();
        }

        if (!this.isOpen(row, col)){
            this.gird[row - 1][col - 1].isOpen = true;
            this.numberOfOpenSites += 1;
            if (row == 1) {
                this.gird[row - 1][col - 1].isFull = true;
            }

            if (!(this.size == 1)){
                if (row == 1 && col == 1){
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                } else if (row == 1 && col == this.size) {
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                } else if (row == this.size && col == 1) {
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                } else if (row == this.size && col == this.size) {
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                    if (this.gird[row - 1][col - 2].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        if (this.gird[row - 1][col - 2].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        }
                    }
                } else if (row == 1 && col < this.size) {
                    if (this.gird[row - 1][col - 2].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        if (this.gird[row - 1][col - 2].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        }
                    }
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                } else if (row == this.size && col < this.size) {
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                    if (this.gird[row - 1][col - 2].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        }
                    }
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                } else if (row < this.size && col == 1) {
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                } else if (row < this.size && col == this.size) {
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                    if (this.gird[row - 1][col - 2].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        if (this.gird[row - 1][col - 2].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        }
                    }
                } else {
                    if (this.gird[row - 2][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        if (this.gird[row - 2][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 2][col - 1].number);
                        }
                    }
                    if (this.gird[row][col - 1].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        if (this.gird[row][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row][col - 1].number);
                        }
                    }
                    if (this.gird[row - 1][col - 2].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        if (this.gird[row - 1][col - 2].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col - 2].number);
                        }
                    }
                    if (this.gird[row - 1][col].isOpen) {
                        this.openUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        if (this.gird[row - 1][col - 1].isFull) {
                            this.gird[row - 1][col - 1].isFull = true;
                            this.percolateUF.union(this.gird[row - 1][col - 1].number, this.gird[row - 1][col].number);
                        }
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
        return this.gird[row - 1][col - 1].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
        return this.gird[row - 1][col - 1].isFull;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.percolateUF.connected(this.visualTop, this.visualBottom);
    }

}
