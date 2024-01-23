public class Node {
    public int row, column;
    public int number;
    public int blockNumber; // Numerical values of each 3x3 squares (1 to 9)
    public boolean isFree; // For checking default/non-changeble numbers

    public Node(int row, int column, int number, boolean isFree) {
        this.row = row;
        this.column = column;
        this.number = number;
        this.isFree = isFree;

        if (row < 3 && column < 3) blockNumber = 1;
        else if (row < 3 && column < 6) blockNumber = 2;
        else if (row < 3 && column < 9) blockNumber = 3;
        else if (row < 6 && column < 3) blockNumber = 4;
        else if (row < 6 && column < 6) blockNumber = 5;
        else if (row < 6 && column < 9) blockNumber = 6;
        else if (row < 9 && column < 3) blockNumber = 7;
        else if (row < 9 && column < 6) blockNumber = 8;
        else if (row < 9 && column < 9) blockNumber = 9;
        else throw new IllegalArgumentException();
    }
}
