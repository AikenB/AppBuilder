import java.util.Scanner;

public class TicTacToe {



    //NOTE: THIS IS JUST AN EXAMPLE OF HOW TO CREATE TIC-TAC-TOE, THERE ARE OTHER WAYS TO DO THIS WHICH ARE SIMPLER
    
    String[][] board = new String[3][3]; //creates the board
    //creates the variables for the inputs. These are set to 1 to avoid erroring the code
    int rowInput = 1; 
    int colInput = 1;
    boolean gameEnd = false; //this variable is just there to force the loop to run until a player wins
    Scanner input = new Scanner(System.in); //creates Scanner object called input
    
    void initializeBoard(){ // sets up board
         for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = " ";
            }
        }
    }
    void printBoard(){ //prints out the board onto the screen
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
    void plotPoint(int row, int col, String marking){//marks a point on the board
        board[row][col] = marking;
    }
    
    String checkWin(){ //checks every possible 3-in-a row and will return the X or O depending on who made the 3-in a row. returns null if condition not met.
        if (board[0][0].equals(board[0][1]) && board[0][1].equals(board[0][2])){
            return board[0][0];
        }
        else if (board[1][0].equals(board[1][1]) && board[1][1].equals(board[1][2])){
            return board[1][0];
        }
        else if (board[2][0].equals(board[2][1]) && board[2][1].equals(board[2][2])){
            return board[2][0];
        }
        else if (board[0][0].equals(board[1][0]) && board[1][0].equals(board[2][0])){
            return board[0][0];
        }
        else if (board[0][1].equals(board[1][1]) && board[1][1].equals(board[2][1])){
            return board[0][1];
        }
        else if (board[0][2].equals(board[1][2]) && board[1][2].equals(board[2][2])){
            return board[0][2];
        }
        else if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])){
            return board[0][0];
        }
        else if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])){
            return board[0][2];
        }
        else return " "; //will return nothing if nobody has won yet

    }

    void runTicTacToe(){
        
        
        //Set up Board
        initializeBoard(); 
        printBoard();
        
        while (gameEnd == false){ //runs game until a player wins
            
            while (!(board[rowInput -1][colInput -1].equals("X"))) {//keeps asking for an input until an X is plotted
                System.out.println("Player 1 choose a row");
                rowInput = input.nextInt(); //sets the row input
                System.out.println("Player 1 choose a column");
                colInput = input.nextInt(); //sets column input
                plotPoint(rowInput - 1, colInput - 1, "X"); //plots X based on input
            }
            printBoard(); //prints updated board
            if (checkWin().equals("X")){ //checks if player 1 has won
                System.out.println("player 1 wins!");
                break; //stops the loop immediately
            }
            
            while (!board[rowInput -1][colInput -1].equals("O")){// keeps asking for an input until an O is plotted
                System.out.println("Player 2 choose a row");
                rowInput = input.nextInt(); //sets row input
                System.out.println("Player 2 choose a column");
                colInput = input.nextInt(); //sets column input
                plotPoint(rowInput - 1, colInput - 1, "O"); //plots O based on input
            }
            printBoard(); //prints updated board
            if (checkWin().equals("O")){ //checks if player 2 has won
                System.out.println("Player 2 wins!");
                break; //stops the loop immediately
            }
            
        }
    }
    
    
}
