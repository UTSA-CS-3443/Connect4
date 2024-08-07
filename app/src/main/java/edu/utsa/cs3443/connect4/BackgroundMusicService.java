package edu.utsa.cs3443.connect4;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {
    private MediaPlayer player;

    // Receiver to stop the service on specified actions (e.g., screen off)
    private final BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Stop the service when the screen is off or other specified actions are detected
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                stopSelf();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize MediaPlayer with background music and set it to loop
        player = MediaPlayer.create(this, R.raw.hero);
        player.setLooping(true);
        player.setVolume(100, 100);

        // Registering the receiver for screen off action
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(stopServiceReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Ensure MediaPlayer is initialized and start playing if not already
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.hero);  // ensure your resource file
            player.setLooping(true);
        }
        if (!player.isPlaying()) {
            player.start();
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop and release MediaPlayer resources
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
        }
        // Unregister the receiver
        unregisterReceiver(stopServiceReceiver);
    }

}

