package edu.utsa.cs3443.connect4.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

import edu.utsa.cs3443.connect4.model.Board;
import edu.utsa.cs3443.connect4.model.Piece;
import edu.utsa.cs3443.connect4.model.Player;

public class BoardView extends View {

    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Paint paint;
    private Paint imagePaint;

    private OnGameEndListener onGameEndListener;
    private OnTurnChangeListener onTurnChangeListener;

    private int player1Color;
    private int player2Color;
    private Bitmap player1Image;
    private Bitmap player2Image;

    public interface OnGameEndListener {
        void onGameEnd(String winnerMessage);
    }

    public interface OnTurnChangeListener {
        void onTurnChange(Player currentPlayer);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        board = new Board();
        Piece piece1 = new Piece();
        piece1.setState(Piece.State.PLAYER_ONE);
        Piece piece2 = new Piece();
        piece2.setState(Piece.State.PLAYER_TWO);
        player1 = new Player(piece1);
        player2 = new Player(piece2);
        currentPlayer = player1;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        imagePaint = new Paint();
        imagePaint.setAntiAlias(true);

        updatePreferences(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        updatePreferences(getContext());

        int cellWidth = getWidth() / 7;
        int cellHeight = getHeight() / 6;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    if (piece.getState() == Piece.State.PLAYER_ONE && player1Image != null) {
                        drawCircularImage(canvas, player1Image, col, row, cellWidth, cellHeight);
                    } else if (piece.getState() == Piece.State.PLAYER_TWO && player2Image != null) {
                        drawCircularImage(canvas, player2Image, col, row, cellWidth, cellHeight);
                    } else {
                        switch (piece.getState()) {
                            case PLAYER_ONE:
                                paint.setColor(player1Color);
                                break;
                            case PLAYER_TWO:
                                paint.setColor(player2Color);
                                break;
                            default:
                                paint.setColor(android.graphics.Color.WHITE);
                                break;
                        }
                        canvas.drawOval(new RectF(col * cellWidth, row * cellHeight, (col + 1) * cellWidth, (row + 1) * cellHeight), paint);
                    }
                } else {
                    paint.setColor(android.graphics.Color.WHITE);
                    canvas.drawOval(new RectF(col * cellWidth, row * cellHeight, (col + 1) * cellWidth, (row + 1) * cellHeight), paint);
                }
            }
        }
    }

    private void drawCircularImage(Canvas canvas, Bitmap image, int col, int row, int cellWidth, int cellHeight) {
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
        BitmapShader shader = new BitmapShader(scaledImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        imagePaint.setShader(shader);

        RectF rect = new RectF(col * cellWidth, row * cellHeight, (col + 1) * cellWidth, (row + 1) * cellHeight);
        canvas.drawOval(rect, imagePaint);
    }

    private void updatePreferences(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        player1Color = prefs.getInt("player1_color", android.graphics.Color.RED);
        player2Color = prefs.getInt("player2_color", android.graphics.Color.YELLOW);

        String player1ImageString = prefs.getString("player1_image", null);
        if (player1ImageString != null) {
            byte[] imageBytes = Base64.decode(player1ImageString, Base64.DEFAULT);
            player1Image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            player1Image = null;
        }

        String player2ImageString = prefs.getString("player2_image", null);
        if (player2ImageString != null) {
            byte[] imageBytes = Base64.decode(player2ImageString, Base64.DEFAULT);
            player2Image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            player2Image = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) (event.getX() / (getWidth() / 7));
            if (board.move(currentPlayer, col)) {
                if (board.gameWin(currentPlayer)) {
                    if (onGameEndListener != null) {
                        String winnerMessage = "Player " + (currentPlayer == player1 ? "1" : "2") + " wins!";
                        onGameEndListener.onGameEnd(winnerMessage);
                    }
                } else if (board.gameTie()) {
                    if (onGameEndListener != null) {
                        onGameEndListener.onGameEnd("It's a tie!");
                    }
                } else {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                    if (onTurnChangeListener != null) {
                        onTurnChangeListener.onTurnChange(currentPlayer);
                    }
                }
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnGameEndListener(OnGameEndListener listener) {
        this.onGameEndListener = listener;
    }

    public void setOnTurnChangeListener(OnTurnChangeListener listener) {
        this.onTurnChangeListener = listener;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}