package com.project.tantibus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG_LOG = MenuActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //start a new game
        final Button newGame_button = findViewById(R.id.newgame_button);
        newGame_button.setOnClickListener(view -> newGame());

        //load a started game
        final Button loadGame_button = findViewById(R.id.loadgame_button);
        loadGame_button.setOnClickListener(view -> loadGame());

        //show credits
        final Button credits_button = findViewById(R.id.credits_button);
        credits_button.setOnClickListener(view -> credits());
    }

        //START A NEW GAME
    private void newGame()
    {
        Log.d(TAG_LOG, "New game access");
        final Intent SignupActivity = new Intent(MenuActivity.this, SignupActivity.class);
        startActivity(SignupActivity);
    }

        //CONTINUE A GAME
    private void loadGame()
    {
        Log.d(TAG_LOG, "Load game access");
        final Intent LoginActivity = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(LoginActivity);
    }

        //SHOW CREDITS
    private void credits()
    {
        Log.d(TAG_LOG, "Credits access");
        final Intent CreditsActivity = new Intent(MenuActivity.this, CreditsActivity.class);
        startActivity(CreditsActivity);
    }
}