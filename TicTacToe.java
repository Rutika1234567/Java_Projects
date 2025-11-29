import java.util.Scanner;

class TicTacToe {
public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    char[][] board = new char[3][3];

    // initialize board
    for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board[row].length; col++) {
            board[row][col] = ' ';
        }
    }

    char player = 'x';
    boolean gameOver = false;

    while (!gameOver) {
        printBoard(board);

        System.out.println("Player " + player + ": Enter row and column (0-2): ");
        int row = scan.nextInt();
        int col = scan.nextInt();

        // checking valid position
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            System.out.println("Invalid position! Try again.");
            continue;
        }

        if (board[row][col] == ' ') {

            board[row][col] = player;

            gameOver = haveWon(board, player);
            if (gameOver) {
                System.out.println("Congratulations, Player " + player + " has won!");
            } else {
                player = (player == 'x') ? 'o' : 'x';
            }

        } else {
            System.out.println("Invalid move. Try again!");
        }
    }

    printBoard(board);
}

public static boolean haveWon(char[][] board, char player) {

    // check rows
    for (int row = 0; row < 3; row++) {
        if (board[row][0] == player &&
            board[row][1] == player &&
            board[row][2] == player) {
            return true;
        }
    }

    // check columns
    for (int col = 0; col < 3; col++) {
        if (board[0][col] == player &&
            board[1][col] == player &&
            board[2][col] == player) {
            return true;
        }
    }

    // check diagonals
    if (board[0][0] == player &&
        board[1][1] == player &&
        board[2][2] == player) {
        return true;
    }

    if (board[0][2] == player &&
        board[1][1] == player &&
        board[2][0] == player) {
        return true;
    }

    return false;
}

public static void printBoard(char[][] board) {
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            System.out.print(board[row][col] + " | ");
        }
        System.out.println();
    }
  }

}
