package com.example.stopwatch; // Adjust the package name as needed

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
    Button btnstart, btnreset, btnpause, btnstop;
    ImageView icanchor;
    Animation roundingalone;
    Chronometer timerHere;
    boolean isPaused = false; // To track whether the timer is paused or not
    long pausedTime = 0; // To store elapsed time when paused

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        btnstart = findViewById(R.id.btnstart);
        btnpause = findViewById(R.id.btnpause);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);
        btnreset = findViewById(R.id.btnreset);

        // create optional animation
        btnstop.setAlpha(0);

        // load animations
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);

        //import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        // Customize font for buttons
        btnstart.setTypeface(MMedium);
        btnstop.setTypeface(MMedium);
        btnpause.setTypeface(MMedium);
        btnreset.setTypeface(MMedium);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    // Only start the timer if it's not paused
                    icanchor.startAnimation(roundingalone);
                    btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                    btnstart.animate().alpha(0).setDuration(300).start();
                    // Start Time
                    timerHere.setBase(SystemClock.elapsedRealtime() - pausedTime);
                    timerHere.start();
                } else {
                    // Resume the timer from where it was paused
                    long currentTime = SystemClock.elapsedRealtime();
                    timerHere.setBase(currentTime - pausedTime);
                    timerHere.start();
                    icanchor.startAnimation(roundingalone); // Start the animation again
                }
                isPaused = false; // Timer is no longer paused

                // Hide the Start button
                btnstart.animate().alpha(0).setDuration(300).start();
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    // Pause the timer if it's running
                    timerHere.stop();
                    icanchor.clearAnimation(); // Stop the animation
                    pausedTime = SystemClock.elapsedRealtime() - timerHere.getBase(); // Store elapsed time
                    isPaused = true; // Timer is now paused

                    // Make the Start button visible
                    btnstart.animate().alpha(1).setDuration(300).start();
                }
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the timer
                timerHere.stop();
                icanchor.clearAnimation(); // Stop the animation
                btnstop.animate().alpha(0).translationY(0).setDuration(300).start();
                btnstart.animate().alpha(1).setDuration(300).start();
                isPaused = false; // Reset the pause state
                pausedTime = 0; // Reset the stored elapsed time
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the timer
                timerHere.stop();
                timerHere.setBase(SystemClock.elapsedRealtime());
                icanchor.clearAnimation(); // Stop the animation
                btnstop.animate().alpha(0).translationY(0).setDuration(300).start();
                btnstart.animate().alpha(1).setDuration(300).start();
                isPaused = false; // Reset the pause state
                pausedTime = 0; // Reset the stored elapsed time
            }
        });
    }
}
