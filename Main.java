public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver("sudoku.txt");
        if (solver.solve()) {
            System.out.println("The given sudoku was successfully solved.");
            solver.sudoku.printSudoku();
        } else {
            System.out.println("The given sudoku could not be solved.");
        }
    }
}
