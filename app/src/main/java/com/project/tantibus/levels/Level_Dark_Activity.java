package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

public class Level_Dark_Activity extends AppCompatActivity {


    //---LEVEL DARK---//
    //---REST---//

    //final static int current_level = 10;
    final static int finish_game_level = 23;

    //Moon phase count
    int moon_phase = 1;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_dark);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //IMPORT MOON ICON AND CHANGE PHASE
        ImageView moon_icon = findViewById(R.id.moon_image);
        moon_icon.setOnClickListener(view -> ChangePhase());

        //CHECK WIN CONDITION
        Button moon_button = findViewById(R.id.button_final);
        moon_button.setOnClickListener(view -> {
            if(moon_phase==0){
                LevelPassed();
            }else{
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.Secret_Level_Fail), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //change moon image
    private void ChangePhase(){
        ImageView moon_icon = findViewById(R.id.moon_image);

        if(moon_phase==1){
            moon_icon.setImageResource(R.drawable.moon2);
            moon_phase=2;
        }else if(moon_phase==2){
            moon_icon.setImageResource(R.drawable.moon3);
            moon_phase=3;
        }else if(moon_phase==3){
            moon_icon.setImageResource(R.drawable.moon4);
            moon_phase=4;
        }else if(moon_phase==4){
            moon_icon.setImageResource(R.drawable.moon5);
            moon_phase=5;
        }else if(moon_phase==5){
            moon_icon.setImageResource(R.drawable.moon6);
            moon_phase=6;
        }else if(moon_phase==6){
            moon_icon.setImageResource(R.drawable.moon7);
            moon_phase=7;
        }else if(moon_phase==7){
            moon_icon.setImageResource(R.drawable.moon8);
            moon_phase=8;
        }else if(moon_phase==8){
            moon_icon.setImageResource(R.drawable.moon9);
            moon_phase=9;
        }else if(moon_phase==9){
            moon_icon.setImageResource(R.drawable.moon10);
            moon_phase=10;
        }else if(moon_phase==10){
            moon_icon.setImageResource(R.drawable.moon11);
            moon_phase=11;
        }else if(moon_phase==11){
            moon_icon.setImageResource(R.drawable.moon0);
            moon_phase=0;
        }else if(moon_phase==0){
            moon_icon.setImageResource(R.drawable.moon1);
            moon_phase=1;
        }
    }

    //go back to the level menu
    private void LevelMenu(){
        //call database
        DB = new DBHelper(this);
        //check max level player has reached
        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);
        //check if this is the new max level
        if(checkLevel<finish_game_level){
            //suggest to think about
            Toast.makeText(Level_Dark_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level_Dark_Activity.this, com.project.tantibus.LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

    //Level passed
    private void LevelPassed() {

        //call database
        DB = new DBHelper(this);
        //check max level player has reached

        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);
        //check if this is the new max level
        if(checkLevel<finish_game_level){
            //UPDATE THE MAX LEVEL
            Boolean UpdateLevel = DB.UpdateLevel(GlobalClass.GlobalUser,finish_game_level);
            if(UpdateLevel){
                //CONFIRM UPDATE LEVEL AND BACK TO LEVEL MENU
                Toast.makeText(Level_Dark_Activity.this,getResources().getString(R.string.Secret_Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = finish_game_level;
                final Intent LevelActivity = new Intent(Level_Dark_Activity.this, LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level_Dark_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(this,getResources().getString(R.string.Secret_Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level_Dark_Activity.this, LevelActivity.class);
            startActivity(LevelActivity);
            finish();
        }

    }
}