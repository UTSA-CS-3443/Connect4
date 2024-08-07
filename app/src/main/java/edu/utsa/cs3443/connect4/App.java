package edu.utsa.cs3443.connect4;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static App instance;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(this);
    }

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // Not used here
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground, start the music service
            startMusicService();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background, stop the music service
            stopMusicService();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // Not used here
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Not used here
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // Not used here
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // Not used here
    }

    private void startMusicService() {
        Intent musicServiceIntent = new Intent(this, BackgroundMusicService.class);
        startService(musicServiceIntent);
    }

    private void stopMusicService() {
        Intent musicServiceIntent = new Intent(this, BackgroundMusicService.class);
        stopService(musicServiceIntent);
    }
}
