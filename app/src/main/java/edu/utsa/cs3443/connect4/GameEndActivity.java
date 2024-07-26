package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        TextView winnerMessageTextView = findViewById(R.id.winnerMessageTextView);
        String winnerMessage = getIntent().getStringExtra("WINNER_MESSAGE"); // Update this line
        winnerMessageTextView.setText(winnerMessage);
    }

    public void playAgain(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        finish();
    }
}