package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

public class Level6_Activity extends AppCompatActivity {

    //---LEVEL 6---//
    //---STARS---//

    //final static int current_level = 6;
    final static int next_level = 7;

    DBHelper DB;

    //set extremes of pickers
    final static int min_value = 0;
    final static int max_value = 9;
    //store the 3 numbers
    int password_1 = 0;
    int password_2 = 0;
    int password_3 = 0;
    //solution
    final static int solution_1 = 1;
    final static int solution_2 = 3;
    final static int solution_3 = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level6);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //Call and set number pickers
        NumberPicker numberPicker1 = findViewById(R.id.picker_1);
        numberPicker1.setMinValue(min_value);
        numberPicker1.setMaxValue(max_value);
        NumberPicker numberPicker2 = findViewById(R.id.picker_2);
        numberPicker2.setMinValue(min_value);
        numberPicker2.setMaxValue(max_value);
        NumberPicker numberPicker3 = findViewById(R.id.picker_3);
        numberPicker3.setMinValue(min_value);
        numberPicker3.setMaxValue(max_value);

        //get number of 1st picker
        numberPicker1.setOnValueChangedListener((picker, oldVal, newVal) -> password_1 = newVal);
        //get number of 2nd picker
        numberPicker2.setOnValueChangedListener((picker, oldVal, newVal) -> password_2 = newVal);
        //get number of 3rd picker
        numberPicker3.setOnValueChangedListener((picker, oldVal, newVal) -> password_3 = newVal);

        //button to check  level condition
        final Button picker_button = findViewById(R.id.button_picker);
        picker_button.setOnClickListener(view -> {
            if(password_1==solution_1&&password_2==solution_2&&password_3==solution_3){
                LevelPassed();
            }  else {Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Not_passed), Toast.LENGTH_SHORT);
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
                Toast.makeText(Level6_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level6_Activity.this, com.project.tantibus.LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level6_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level6_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level6_Activity.this, LevelActivity.class);
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
            //suggest to think about
            Toast.makeText(Level6_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level6_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}