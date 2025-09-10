package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

public class Level9_Activity extends AppCompatActivity {

    //---LEVEL 9---//
    //---REVEAL---//

    //final static int current_level = 9;
    final static int secret_level = 10;

    DBHelper DB;

    EditText texted_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level9);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //Button to check level condition
        final Button button_glass = findViewById(R.id.button_glass);

        button_glass.setOnClickListener(view -> {

            //GET TEXTED USERNAME
            texted_username = findViewById(R.id.Reversed_user_text);
            String user = texted_username.getText().toString();
            //CREATE REVERSED STRING
            StringBuilder Reversed_username = new StringBuilder();
            //PUT USER IN AN ARRAY and REORDER
            char[]EnterString = user.toCharArray();
            for(int i =EnterString.length-1; i>=0; i--){
                Reversed_username.append(EnterString[i]);
            }
            //Toast.makeText(Level9_Activity.this, Reversed_username, Toast.LENGTH_LONG).show();

            //CHECK IF REVERSED IS EQUAL TO USERNAME
            if (GlobalClass.GlobalUser.equals(Reversed_username.toString())){
                LevelPassed();
            }else{
                Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.lv9_wrong), Toast.LENGTH_SHORT);
                toast_fail.setGravity(Gravity.TOP, 0,100);
                toast_fail.show();
            }
        });
    }

    //level passed
    private void LevelPassed() {

        //call database
        DB = new DBHelper(this);
        //check max level player has reached

        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);
        //check if this is the new max level
        if(checkLevel<secret_level){
            //UPDATE THE MAX LEVEL
            Boolean UpdateLevel = DB.UpdateLevel(GlobalClass.GlobalUser,secret_level);
            if(UpdateLevel){
                //CONFIRM UPDATE LEVEL AND BACK TO LEVEL MENU
                Toast.makeText(Level9_Activity.this,getResources().getString(R.string.Final_Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = secret_level;
                final Intent LevelActivity = new Intent(Level9_Activity.this, com.project.tantibus.LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level9_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level9_Activity.this,getResources().getString(R.string.Final_Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level9_Activity.this, LevelActivity.class);
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
        if(checkLevel<secret_level){
            //suggest to think about
            Toast.makeText(Level9_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level9_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}