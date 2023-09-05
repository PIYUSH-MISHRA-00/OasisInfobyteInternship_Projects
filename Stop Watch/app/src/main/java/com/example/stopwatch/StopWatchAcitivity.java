package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class StopWatchActivity extends AppCompatActivity {
    Button btnstart, btnpause, btnstop;
    ImageView icanchor;
    Animation roundingalone;

    Chronometer timerHere;
    boolean isPaused = false; // To track whether the timer is paused or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch_activity);

        btnstart = findViewById(R.id.btnstart);
        btnpause = findViewById(R.id.btnpause);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

        // create optional animation
        btnstop.setAlpha(0);

        // load animations
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);

        //import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");

        // Customize font
        btnstart.setTypeface(MMedium);
        btnstop.setTypeface(MMedium);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    // Only start the timer if it's not paused
                    icanchor.startAnimation(roundingalone);
                    btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                    btnstart.animate().alpha(0).setDuration(300).start();
                    // Start Time
                    timerHere.setBase(SystemClock.elapsedRealtime());
                    timerHere.start();
                } else {
                    // Resume the timer if it's paused
                    long pausedTime = SystemClock.elapsedRealtime() - timerHere.getBase();
                    timerHere.setBase(SystemClock.elapsedRealtime() - pausedTime);
                    timerHere.start();
                }
                isPaused = false; // Timer is no longer paused
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    // Pause the timer if it's running
                    timerHere.stop();
                    isPaused = true; // Timer is now paused
                }
            }
        });
    }
}
