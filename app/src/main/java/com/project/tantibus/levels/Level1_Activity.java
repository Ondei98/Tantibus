package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;
import android.provider.Settings;  // for global system settings
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;


public class Level1_Activity extends AppCompatActivity {

    //---LEVEL 1---//
    //---AWAKE---//

    //final static int current_level = 1;
    final static int next_level = 2;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //Button to check level condition
        final Button button_brightness = findViewById(R.id.button_brightness);

        button_brightness.setOnClickListener(view -> {

            //GET VOLUME
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int volume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            //Toast.makeText(Level1_Activity.this,String.valueOf(volume), Toast.LENGTH_LONG).show();

            //GET BRIGHTNESS
            int intBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 50);
            //Toast.makeText(Level1_Activity.this,String.valueOf(intBrightness), Toast.LENGTH_LONG).show();

            //AND CHECK
            if (intBrightness<=50 && volume<=10) {LevelPassed();}
            else {Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Not_passed), Toast.LENGTH_SHORT);
                toast_fail.setGravity(Gravity.TOP, 0,100);
                toast_fail.show();}

        });

    }

    //Level passed
    private void LevelPassed() {

        //call database
        DB = new DBHelper(this);
        //check max level player has reached

        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);
            //check if this is the new max level
        if(checkLevel<next_level){
            //UPDATE THE MAX LEVEL
            Boolean UpdateLevel = DB.UpdateLevel(GlobalClass.GlobalUser,next_level);
            if(UpdateLevel){
                //CONFIRM UPDATE LEVEL AND BACK TO LEVEL MENU
                Toast.makeText(Level1_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level1_Activity.this, LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level1_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level1_Activity.this, LevelActivity.class);
               startActivity(LevelActivity);
               finish();
        }

    }

    //go back to the level menu
    private void LevelMenu(){
        //call database
        DB = new DBHelper(this);
        //check max level player has reached
        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);
        //check if this is the new max level
        if(checkLevel<next_level){
            //suggest to think
            Toast.makeText(Level1_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level1_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }
}