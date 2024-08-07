package edu.utsa.cs3443.connect4.model;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Piece {
    private int color;
    private State state;

    public enum State {
        EMPTY, PLAYER_ONE, PLAYER_TWO
    }

    // Default constructor, initializes piece as empty
    public Piece() {
        this.state = State.EMPTY; // All pieces start as empty
    }

    // Constructor to accept initial state
    public Piece(State state) {
        this.state = state;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    // Create a drawable representation of the piece
    public ShapeDrawable getDrawable() {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.setIntrinsicWidth(100); // Set the size of the circle
        drawable.setIntrinsicHeight(100);

        switch (state) {
            case PLAYER_ONE:
                drawable.getPaint().setColor(color);
                break;
            case PLAYER_TWO:
                drawable.getPaint().setColor(color);
                break;
            default:
                drawable.getPaint().setColor(android.graphics.Color.WHITE);
                break;
        }
        return drawable;
    }
}
