package edu.utsa.cs3443.connect4;

public class Player {
    private Piece mark;

    public Player(Piece mark) {
        this.mark = mark;
    }

    public Piece getMark() {
        return mark;
    }

    public void setMark(Piece mark) {
        this.mark = mark;
    }
}
