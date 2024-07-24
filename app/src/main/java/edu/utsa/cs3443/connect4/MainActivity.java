package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGame = findViewById(R.id.button);
        startGame.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
            startActivity(intent);
        });
        Button how2Play = findViewById(R.id.button1);
        how2Play.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
            startActivity(intent);
        });
        Button settings = findViewById(R.id.button2);
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        Button credits = findViewById(R.id.button3);
        credits.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
            startActivity(intent);
        });
    }
}