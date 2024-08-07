package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class GameEndActivity extends AppCompatActivity {
    private int player1Wins = 0;
    private int player2Wins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        // Display the winner message
        TextView winnerMessageTextView = findViewById(R.id.winnerMessageTextView);
        String winnerMessage = getIntent().getStringExtra("WINNER_MESSAGE"); // Update this line
        winnerMessageTextView.setText(winnerMessage);

        // Update win counts and write the result to a file
        updateWinCounts(winnerMessage);
        writeGameResultToFile(winnerMessage);
    }

    // Update the win counts based on the winner message
    private void updateWinCounts(String winnerMessage) {
        if (winnerMessage.contains("Player 1")) {
            player1Wins++;
        } else if (winnerMessage.contains("Player 2")) {
            player2Wins++;
        }
    }

    // Write the game result to a file
    private void writeGameResultToFile(String winnerMessage) {
        String filename = "connect4_results.txt";
        String fileContents = winnerMessage + ", Player 1 Wins: " + player1Wins + ", Player 2 Wins: " + player2Wins + "\n";
        try (FileOutputStream fos = openFileOutput(filename, MODE_APPEND)) {
            fos.write(fileContents.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Button click handler to play again
    public void playAgain(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        finish();
    }
}