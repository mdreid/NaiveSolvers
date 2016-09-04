import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SudokuSolver {

    public static boolean solveSudoku(int row, int col, int[][] board) {
        int newCol = col;
        int newRow = row;
        boolean finished = false;

        if (col < board.length - 1) {
            newCol = col + 1;
        }
        else if (row < board.length - 1) {
            newRow = row + 1;
            newCol = 0;        
        }
        else {
            finished = true;
        }

        // already given entry
        if (board[row][col] != 0) {
            if (finished) {
                return true;            
            }
            return solveSudoku(newRow, newCol, board);
        }
        else {
            for (int i = 1; i <= 9; i++) {
                if ((validRow(row, i, board) && validCol(col, i, board) && validBox(row, col, i, board))) {
                    board[row][col] = i;
                    if (finished) {
                        // solved entire puzzle
                        return true;
                    }
                    if (solveSudoku(newRow, newCol, board)) {
                        return true;
                    }
                }
            }
            board[row][col] = 0;
            return false;
        }
    }

    private static boolean validRow(int row, int value, int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == value) {
                return false;
            }
        }
        return true;
    }

    private static boolean validCol(int col, int value, int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == value) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean validBox(int row, int col, int value, int[][] board) {
        int rowStart = (row / 3) * 3;
        int colStart = (col / 3) * 3;
        
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (board[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static boolean checkSolution(int[][] startBoard, int[][] finalBoard) {
        // check that we didn't change what was given to us
        for (int i = 0; i < startBoard.length; i++) {
            for (int j = 0; j < startBoard.length; j++) {
                if (startBoard[i][j] != 0 && startBoard[i][j] != finalBoard[i][j]) {
                    System.out.println("Error: Changed an initial value");
                    return false;
                }
                if (finalBoard[i][j] == 0) {
                    return false;
                }
            }
        }

        // check rows and cols
        int sz = startBoard.length;
        for (int i = 0; i < startBoard.length; i++) {
            boolean[] visitedRow = new boolean[sz +1];
            boolean[] visitedCol = new boolean[sz +1];
            for (int j = 0; j < startBoard.length; j++) {
                int v = finalBoard[i][j];
                int t = finalBoard[j][i];
                if (visitedRow[v] || visitedCol[t]) {
                    System.out.println("Error: Duplicate in row or col");
                    return false;
                }
                visitedRow[v] = true;
                visitedCol[t] = true;
            }
        }

        // check Boxes
        for (int i = 0; i < sz; i = i+3) {
            for (int j = 0; j < sz; j = j+3) {
                // check the box starting at (i, j)
                boolean[] visited = new boolean[sz+ 1];
                for (int row = i; row < i+3; row++) {
                    for (int col = j; col < j+3; col++) {
                        int v = finalBoard[row][col];
                        if (visited[v]) {
                            System.out.format("Error: Duplicate in box starting at (%d,%d)\n", i, j);
                            return false;
                        }
                        visited[v] = true;
                    }
                }
            }
        }        
        return true;

    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Program expects one argument: filename of file with board");
            return;
        }
        
        BufferedReader reader = null;
        try { 
            reader = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        int[][] board = new int[9][9];
        int[][] solvedBoard = new int[9][9];
        
        try {
            for (int i = 0; i < 9; i++) {
                String row = reader.readLine();
                String[] entries = row.split(",");
                for (int j = 0; j < entries.length; j++) {
                    int v = Integer.parseInt(entries[j]);
                    board[i][j] = v;
                    solvedBoard[i][j] = v;
                }
            }        
        } catch (IOException e) {
            e.printStackTrace();
        }
        printBoard(board);
        System.out.println("Solved? " + (solveSudoku(0, 0, solvedBoard) && checkSolution(board, solvedBoard)));
        printBoard(solvedBoard);
    }
}
