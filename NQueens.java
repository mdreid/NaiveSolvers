import java.util.Set;
import java.util.HashSet;

public class NQueens {
    
    public static Set<boolean[][]> findValidBoards(int n) {
        boolean[][] board = new boolean[n][n];
        Set<boolean[][]> results = new HashSet<>();
        findValidBoards(n, board, results);
        return results;
    }

    public static void addBoard(boolean[][] board, Set<boolean[][]> results) {
        boolean[][] newBoard = new boolean[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        results.add(newBoard);
    }

    public static void findValidBoards(int n, boolean[][] board, Set<boolean[][]> results) {
        if (n == 0) {
            addBoard(board, results);
            return;
        }       
            for (int j = 0; j < board.length; j++) {
                if (!hasConflicts(board, board.length-n, j)) {
                    //System.out.println("No conflicts");
                    board[board.length-n][j] = true;
                    findValidBoards(n-1, board, results);
                    board[board.length-n][j] = false;
                }
            }
    }

    public static boolean hasConflicts(boolean[][] board, int row, int col) {
        for (int k = 0; k < board.length; k++) {
            if (board[k][col]) {
                return true;
            }
        }
        for (int k = row+1, l = col+1; k < board.length && l < board.length; k++, l++) {
           if (board[k][l]) {
                return true;
            } 
        }
        for (int k = row+1, l = col-1; k < board.length && l >= 0; k++, l--) {
            if (board[k][l]) {
                return true;
            }
        }
        for (int k = row-1, l = col-1; k >= 0 && l >= 0; k--, l--) {
            if (board[k][l]) {
                return true;
            }
        }
        for (int k = row-1, l = col+1; k >= 0 && l < board.length; k--, l++) {
            if (board[k][l]) {
                return true;
            }
        }
        return false;
    }

    private static void printBoard(boolean[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("=============");
    }

    public static void main(String[] args) {
        Set<boolean[][]> results = findValidBoards(8);
        for (boolean[][] array : results) {
            printBoard(array);
        }
        System.out.println("Number of possible configurations: " + results.size());
    }

}
