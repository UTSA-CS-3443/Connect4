package edu.utsa.cs3443.connect4.model;

public class Board {
    private Piece[][] board;
    private int[] bottom = {5, 5, 5, 5, 5, 5, 5};

    public Board() {
        board = new Piece[6][7];
        // Initialize the board with empty pieces
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                board[row][col] = new Piece();
            }
        }
    }

    public boolean move(Player p, int col) {
        if (col >= 0 && col <= 6 && bottom[col] >= 0 && board[bottom[col]][col].getState() == Piece.State.EMPTY) {
            board[bottom[col]][col].setState(p.getMark().getState());
            board[bottom[col]][col].setColor(p.getMark().getColor());
            bottom[col]--;
            return true;
        } else {
            return false;
        }
    }

    public boolean gameWin(Player p) {
        return hMatch(p) || vMatch(p) || leftDiagMatch(p) || rightDiagMatch(p);
    }

    private boolean hMatch(Player p) {
        int m = p.getMark().getState().ordinal();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c].getState().ordinal() == m &&
                        board[r][c + 1].getState().ordinal() == m &&
                        board[r][c + 2].getState().ordinal() == m &&
                        board[r][c + 3].getState().ordinal() == m) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean vMatch(Player p) {
        int m = p.getMark().getState().ordinal();
        for (int c = 0; c < 7; c++) {
            for (int r = 0; r < 3; r++) {
                if (board[r][c].getState().ordinal() == m &&
                        board[r + 1][c].getState().ordinal() == m &&
                        board[r + 2][c].getState().ordinal() == m &&
                        board[r + 3][c].getState().ordinal() == m) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean leftDiagMatch(Player p) {
        int m = p.getMark().getState().ordinal();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c].getState().ordinal() == m &&
                        board[r + 1][c + 1].getState().ordinal() == m &&
                        board[r + 2][c + 2].getState().ordinal() == m &&
                        board[r + 3][c + 3].getState().ordinal() == m) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean rightDiagMatch(Player p) {
        int m = p.getMark().getState().ordinal();
        for (int r = 0; r < 3; r++) {
            for (int c = 3; c < 7; c++) {
                if (board[r][c].getState().ordinal() == m &&
                        board[r + 1][c - 1].getState().ordinal() == m &&
                        board[r + 2][c - 2].getState().ordinal() == m &&
                        board[r + 3][c - 3].getState().ordinal() == m) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameTie() {
        for (int c = 0; c < 7; c++) {
            if (board[0][c].getState() == Piece.State.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }
}
