package com.project.tantibus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

public class CreditsActivity extends AppCompatActivity {

    final static int final_unlock = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        final Button Credits_Secret = findViewById(R.id.credits_image_button);

        //BUTTON FOR THANKS
        Credits_Secret.setOnClickListener(view -> {
            if(GlobalClass.GlobalLevel==final_unlock) {
               Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.thanks1) + "\n"
                                + getResources().getString(R.string.thanks2)+ "\n"
                                + getResources().getString(R.string.thanks3),
                        Toast.LENGTH_LONG);
               toast.setGravity(Gravity.CENTER,0,0);
               toast.show();
            }
        });
    }
}
