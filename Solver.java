import java.util.Random;

public class Solver {
    public Sudoku sudoku;
    private Random rand = new Random();

    public Solver(String fileName) {
        sudoku = new Sudoku(fileName);
    }

    // Randomly swaps 2 numbers in the same 3x3 block
    private Sudoku successor(Sudoku currentTable) {    
        Sudoku newTable = currentTable.copy();
        boolean numberFound = false;

        while (!numberFound) {
            int i1 = rand.nextInt(9);
            int j1 = rand.nextInt(9);
            int i2 = rand.nextInt(9);
            int j2 = rand.nextInt(9);

            if (newTable.array[i1][j1].blockNumber == newTable.array[i2][j2].blockNumber
                && newTable.array[i1][j1].isFree && newTable.array[i2][j2].isFree) {
                int temp = newTable.array[i1][j1].number;
                newTable.array[i1][j1].number = newTable.array[i2][j2].number;
                newTable.array[i2][j2].number = temp;
                numberFound = true;
            }
        }
        return newTable;
    }

    public boolean solve() {
        int faultScore = sudoku.calculateFaultScore();
        System.out.println("Initial Fault Score: " + faultScore);
    
        int iteration = 1;
        double temperature = (double) 1/3;
        double coolingRate = 1/Double.MAX_VALUE;

        for (;iteration < 10_000_000 && temperature > 0; iteration++) {
            if (faultScore == 0) break;

            Sudoku newTable = successor(sudoku);
            int delta = newTable.calculateFaultScore() - faultScore;

            if (delta < 0) {
                sudoku = newTable.copy();
            } else if (rand.nextDouble() < Math.exp(-delta / temperature)) {
                sudoku = newTable.copy();
            }

            temperature *= 1 - coolingRate;
            faultScore = sudoku.calculateFaultScore();

            if (iteration % 50_000 == 0) {
                System.out.println("Iteration: " + iteration + " - Fault Score: " + faultScore);
            }
        }  
        System.out.println("Iteration: " + iteration + " - Fault Score: " + faultScore);
        return faultScore == 0;
    }
}
