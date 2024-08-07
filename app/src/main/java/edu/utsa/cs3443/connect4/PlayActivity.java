package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.utsa.cs3443.connect4.model.Player;
import edu.utsa.cs3443.connect4.view.BoardView;
import edu.utsa.cs3443.connect4.model.Piece;

public class PlayActivity extends AppCompatActivity {

    private BoardView boardView;
    private TextView turnTextView;
    private TextView winTextView;
    private Button settingsButton;
    private Button menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        boardView = findViewById(R.id.boardView);
        turnTextView = findViewById(R.id.turn);
        winTextView = findViewById(R.id.winCount);
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

        boardView.setOnTurnChangeListener(currentPlayer -> updateTurnTextView(currentPlayer));

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
        updateWinCountsFromFile();
    }

    private void updateTurnTextView(Player currentPlayer) {
        String turnText = "Player " + (currentPlayer.getMark().getState() == Piece.State.PLAYER_ONE ? "1" : "2") + "'s turn";
        turnTextView.setText(turnText);
    }

    private void updateWinCountsFromFile(){
        String filename = "connect4_results.txt";
        int p1Wins = 0, p2Wins = 0;

        try (FileInputStream fis = openFileInput(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Player 1 wins")) {
                    p1Wins++;
                }
                if (line.contains("Player 2 wins")) {
                    p2Wins++;
                }
            }
            winTextView.setText("Player 1 Wins: " + p1Wins + ", Player 2 Wins: " + p2Wins);
        } catch (IOException e) {
            Toast.makeText(this, "Error reading from file", Toast.LENGTH_SHORT).show();
        }
    }
}