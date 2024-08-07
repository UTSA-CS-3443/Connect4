package edu.utsa.cs3443.connect4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {
    private Button menuButton;
    private Button player1ColorButton;
    private Button player2ColorButton;
    private Button resetPlayer1ColorButton;
    private Button resetPlayer2ColorButton;
    private Button resetStatsButton;

    private int player1Color = Color.RED;
    private int player2Color = Color.YELLOW;

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        menuButton = findViewById(R.id.menuButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        menuButton.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);

        });
        player1ColorButton = findViewById(R.id.player1ColorButton);
        player2ColorButton = findViewById(R.id.player2ColorButton);
        resetPlayer1ColorButton = findViewById(R.id.resetPlayer1ColorButton);
        resetPlayer2ColorButton = findViewById(R.id.resetPlayer2ColorButton);
        resetStatsButton = findViewById(R.id.resetStatsButton);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        player1Color = prefs.getInt("player1_color", Color.RED);
        player2Color = prefs.getInt("player2_color", Color.YELLOW);

        setButtonColor(player1ColorButton, player1Color);
        setButtonColor(player2ColorButton, player2Color);

        player1ColorButton.setOnClickListener(v -> openColorPicker(1));

        player2ColorButton.setOnClickListener(v -> openColorPicker(2));

        resetPlayer1ColorButton.setOnClickListener(v -> resetPlayer1Color());
        resetPlayer2ColorButton.setOnClickListener(v -> resetPlayer2Color());
        resetStatsButton.setOnClickListener(v -> resetStats());

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0 );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void openColorPicker(final int player) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, player == 1 ? player1Color : player2Color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                if (player == 1) {
                    player1Color = color;
                    setButtonColor(player1ColorButton, player1Color);
                    saveColor("player1_color", player1Color);
                } else {
                    player2Color = color;
                    setButtonColor(player2ColorButton, player2Color);
                    saveColor("player2_color", player2Color);
                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // Do nothing
            }
        });
        dialog.show();
    }

    private void resetPlayer1Color() {
        player1Color = Color.RED;
        setButtonColor(player1ColorButton, player1Color);
        saveColor("player1_color", player1Color);
    }

    private void resetPlayer2Color() {
        player2Color = Color.YELLOW;
        setButtonColor(player2ColorButton, player2Color);
        saveColor("player2_color", player2Color);
    }
    private void resetStats() {
        String filename = "connect4_results.txt";

        try (FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE)) {
            // Writing an empty string to the file to clear its contents
            fos.write("".getBytes());
            Toast.makeText(this, "Stats reset successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error resetting stats", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveColor(String key, int color) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, color);
        editor.apply();
    }

    private void setButtonColor(Button button, int color) {
        button.setBackgroundColor(color);
        if (isColorDark(color)) {
            button.setTextColor(Color.WHITE);
        } else {
            button.setTextColor(Color.BLACK);
        }
    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }
}
