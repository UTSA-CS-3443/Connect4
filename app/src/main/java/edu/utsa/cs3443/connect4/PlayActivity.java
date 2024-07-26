package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.connect4.model.Player;
import edu.utsa.cs3443.connect4.view.BoardView;
import edu.utsa.cs3443.connect4.model.Piece;

public class PlayActivity extends AppCompatActivity {

    private BoardView boardView;
    private TextView turnTextView;
    private Button settingsButton;
    private Button menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        boardView = findViewById(R.id.boardView);
        turnTextView = findViewById(R.id.turn);
        settingsButton = findViewById(R.id.settingsButton);
        menuButton = findViewById(R.id.menuButton);

        boardView.setOnGameEndListener(new BoardView.OnGameEndListener() {
            @Override
            public void onGameEnd(String winnerMessage) {
                Intent intent = new Intent(PlayActivity.this, GameEndActivity.class);
                intent.putExtra("WINNER_MESSAGE", winnerMessage);
                startActivity(intent);
                finish();
            }
        });

        boardView.setOnTurnChangeListener(new BoardView.OnTurnChangeListener() {
            @Override
            public void onTurnChange(Player currentPlayer) {
                updateTurnTextView(currentPlayer);
            }
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        menuButton.setOnClickListener(view -> {
            Intent intent = new Intent(PlayActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Initialize the turn text view with the first player
        updateTurnTextView(boardView.getCurrentPlayer());
    }

    private void updateTurnTextView(Player currentPlayer) {
        String turnText = "Player " + (currentPlayer.getMark().getState() == Piece.State.PLAYER_ONE ? "1" : "2") + "'s turn";
        turnTextView.setText(turnText);
    }
}