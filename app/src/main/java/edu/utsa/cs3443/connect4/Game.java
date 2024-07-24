package edu.utsa.cs3443.connect4;

public class Game {
    private static Player  p1    = new Player(); // PLAYER OBJECTS MAY NEED TO BE CREATED IN MAINACTIVITY CLASS TO KEEP COLOR CHANGE IDK
    private static Player  p2    = new Player();
    private static Player  p;
    private static Board   board = new Board();
    private boolean gameOver = false;
    private boolean goodMove = false;
    private boolean gameWin  = false;
    private boolean gameTie  = false;
    private boolean play     = true;
    private int playerTurn = 1;
    private int col;

    public void gamePlay() {
        while (play) {
            if ( playerTurn == 1 ) {
                p = p1; // p1 goes first
            }
            else {
                p = p2; // p2 goes first
            }

            while (!gameOver) {
                gameOver = false;

                while ( !goodMove ) { // Loop until a valid move is entered.
                    System.out.println("	---" + p.getName() + "'s Turn---"); // NEEDS TO BE CHANGED
                    System.out.print("	Enter col # : ");

                    col = rdInt.nextInt(); // gets input from screen press on specific board column

                    System.out.println();

                    goodMove = board.move(p, col);

                    System.out.println();
                }
                goodMove = false;
                System.out.println(board);
                gameWin = board.gameWin(p);
                gameTie = board.gameTie();

                if ( gameWin || gameTie ) {
                    gameOver = true;
                }

                if ( gameOver ) { //check for rematch
                    System.out.print("	Would you like to play again? (y/n): "); // NEEDS TO BE CHANGED
                    answer = rdLn.nextLine();
                    System.out.println();
                }

                if ( answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no") ) { // NEEDS TO BE CHANGED
                    play = false;
                }
                else { // swaps the turns
                    if ( p == p1 ) {
                        p = p2;
                    }
                    else {
                        p = p1;
                    }
                }
            }
            board = new Board();
            gameOver = false;

            if ( playerTurn == 1 ) { // other player gets to go first
                playerTurn = 2;
            }
            else {
                playerTurn = 1;
            }
        }
    }
}