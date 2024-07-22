package edu.utsa.cs3443.connect4;
//Hopefully this will update in the Github....
public class Board {
    private static Piece[][] board;

    private int[] bottom = { 5, 5, 5, 5, 5, 5, 5 };

    public Board() {
        board = new Piece[6][7];
    }

    public boolean move(Player p, int col) {
        if ( (col <= 6 && col >= 0) && bottom[col] - 1 >= -1 && board[bottom[col]][col] == null ) {
            board[bottom[col]][col] = p.getMark();

            bottom[col]--;

            return true;
        }
        else {
            return false;
        }
    }

    public boolean gameWin(Player p) {
        return hMatch(p) || vMatch(p) || leftDiagMatch(p) || rightDiagMatch(p);
    }

    public boolean hMatch(Player p) {
        int m = p.getMark().getColor();

        for (int r = 5; r >= 0; r--) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c].getColor() == m && board[r][c+1].getColor() == m && board[r][c+2].getColor() == m && board[r][c+3].getColor() == m) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean vMatch(Player p) {
        int m = p.getMark().getColor();

        for (int r = 5; r >= 4; r--) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c].getColor() == m && board[r-1][c].getColor() == m && board[r-2][c].getColor() == m && board[r-3][c].getColor() == m) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean leftDiagMatch(Player p) {
        int m = p.getMark().getColor();

        for (int r = 5; r >= 3; r--) {
            for (int c = 3; c < board[r].length; c++) {
                if (board[r][c].getColor() == m && board[r-1][c-1].getColor() == m && board[r-2][c-2].getColor() == m && board[r-3][c-3].getColor() == m) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean rightDiagMatch(Player p) {
        int m = p.getMark().getColor();

        for (int r = 5; r >= 3; r--) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c].getColor() == m && board[r-1][c+1].getColor() == m && board[r-2][c+2].getColor() == m && board[r-3][c+3].getColor() == m) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean gameTie() {
        for (int c = 0; c < 7; c++) {
            if (board[0][c] == null) {
                return false;
            }
        }

        return true;
    }
}
