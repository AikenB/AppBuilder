public class TicTacToeSetup {

    static String[][] board = new String[3][3]; //creates the board

    static void initializeBoard(){ // sets up board
        for (int row = 0; row < board.length; row++) {
           for (int col = 0; col < board[row].length; col++) {
               board[row][col] = " ";
           }
       }
    }
    static void printBoard(){ //prints out the board onto the screen
       for (int row = 0; row < board.length; row++) {
           for (int col = 0; col < board[row].length; col++) {
               System.out.print(board[row][col]);
               if (col < 2) {
                   System.out.print("|"); 
               }
           }
           System.out.println();
           if (row < 2) {
               System.out.println("-----");
           }
       }
    }
    static void plotPoint(int row, int col, String marking){//marks a point on the board
       board[row][col] = marking;
    }

    
}