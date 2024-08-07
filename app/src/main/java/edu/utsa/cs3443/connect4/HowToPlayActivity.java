package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class HowToPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // Button to return to main menu
        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(view -> {
            Intent intent = new Intent(HowToPlayActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}