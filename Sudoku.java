import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Sudoku {  
    public Node array[][] = new Node[9][9];
    private List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public Sudoku(String fileName) {
        if (fileName.equals("copy")) return;
        createTable(fileName);
    }

    private void createTable(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String lines[] = new String[9];
            
            for (int i = 0; i < 9; i++) {
                lines[i] = br.readLine();
            }

            // Create a 9x9 sudoku array with numbers and empty cells as 0
            for (int i = 0; i < 9; i++) {
                int charIndex = 0;
                for (int j = 0; j < 9; j++) {
                    if (lines[i].charAt(charIndex) == '-') {
                        array[i][j] = new Node(i, j, 0, true);
                    } else {
                        array[i][j] = new Node(i, j, lines[i].charAt(charIndex) - '0', false);
                    }
                    charIndex += 2;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fill empty cells in each 3x3 squares with random numbers 1 to 9
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (array[i][j].number == 0) {
                    ArrayList<Integer> possibleNumbers = new ArrayList<>(numbers);

                    for (int k = 0; k < 9; k++) {
                        for (int l = 0; l < 9; l++) {
                            if (array[k][l].blockNumber == array[i][j].blockNumber) {
                                possibleNumbers.remove((Integer) array[k][l].number);
                            }
                        }
                    }

                    if (!possibleNumbers.isEmpty()) {
                        int randomIndex = rand.nextInt(possibleNumbers.size());
                        array[i][j].number = possibleNumbers.get(randomIndex);
                    }
                }
            }
        }
    }

    // Returns the total fault score for duplicated numbers in rows, columns, blocks
    public int calculateFaultScore() {
        int RowFault = 0, ColumnFault = 0;

        // Calculates the duplicated numbers in all rows
        for (int i = 0; i < 9; i++) {
            int numberCounterList[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            for (int j = 0; j < 9; j++) {
                numberCounterList[array[i][j].number - 1]++;
            }
            for (int duplicateCount : numberCounterList) {
                if (duplicateCount > 1) {
                    RowFault += duplicateCount - 1;
                }
            }
        }

        // Calculates the duplicated numbers in all columns 
        for (int i = 0; i < 9; i++) {
            int numberCounterList[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            for (int j = 0; j < 9; j++) {
                numberCounterList[array[j][i].number - 1]++;
            }
            for (int duplicateCount : numberCounterList) {
                if (duplicateCount > 1) {
                    ColumnFault += duplicateCount - 1;
                }
            }
        }

        return RowFault + ColumnFault;
    }

    public void printSudoku() {
        System.out.println("\nCurrent Sudoku:");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("-------------------------");
            }               
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(array[i][j].number + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------------------");
    }

    public Sudoku copy() {
        Sudoku newTable = new Sudoku("copy");
        newTable.numbers = new ArrayList<>(this.numbers);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newTable.array[i][j] = new Node(this.array[i][j].row, this.array[i][j].column, 
                                                this.array[i][j].number, this.array[i][j].isFree);
            }
        }
        return newTable;
    }
}
