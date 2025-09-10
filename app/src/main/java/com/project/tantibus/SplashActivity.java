package com.project.tantibus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final long MIN_WAIT_INTERVAL = 1000L;
    private long nStartTime = -1L;
    private static final String TAG_LOG = SplashActivity.class.getName();
    private static final String START_TIME_KEY = "com.tantibus.START_TIME_KEY";

    //UPDATE AND SAVE CURRENT TIME
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(START_TIME_KEY, nStartTime);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.nStartTime = savedInstanceState.getLong(START_TIME_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Button logoImageView = findViewById(R.id.splash_image_button);
        //BUTTON TO PROCEED
        logoImageView.setOnClickListener(view -> {
            long elapsedTime = SystemClock.uptimeMillis() - nStartTime;
            // Wait some time to go ahead
            if(elapsedTime>=MIN_WAIT_INTERVAL){
                Log.d(TAG_LOG, "Go Ahead");
                goAhead();
            } else {
                Log.d(TAG_LOG, "Just Wait");
            }
        });

        /* Wait with image and not button
        logoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            boolean onClick(View v, MotionEvent event) {
                long elapsedTime = SystemClock.uptimeMillis() - nStartTime;
                // Wait some time to go ahead
                if(elapsedTime>=MIN_WAIT_INTERVAL){
                    Log.d(TAG_LOG, "Go Ahead");
                    goAhead();
                } else {
                    Log.d(TAG_LOG, "Just Wait");
                }
                return false;
            }
        });*/

    }

    //GET CURRENT TIME
    @Override
    protected void onStart() {
        super.onStart();
        if(nStartTime==-1L){
            nStartTime = SystemClock.uptimeMillis();
        }
        Log.d(TAG_LOG, "Activity started");
    }

    //JUST A LOG
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG_LOG, "Activity destroyed");
    }

    //OPEN NEW ACTIVITY
    private void goAhead() {
        final Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

}