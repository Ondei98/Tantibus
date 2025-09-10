package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;


public class Level5_Activity extends AppCompatActivity {

    //---LEVEL 5---//
    //---ORIGIN---//

    //final static int current_level = 5;
    final static int next_level = 6;

    DBHelper DB;

    //define coordinate of the users (0 is the solution so avoid)
    float latitude_to_check = 23;
    float longitude_to_check = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //Initialize map fragment
        Fragment fragment = new Level5_MapFragment();
        //Open fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        //Button to check level condition
        final Button button_map = findViewById(R.id.button_map);
        //call the map
        button_map.setOnClickListener(view -> {
            //Toast.makeText(Level5_Activity.this,String.valueOf(latitude_to_check) + " , " + String.valueOf(longitude_to_check), Toast.LENGTH_LONG).show();

            //check if the place is right
            if (-0.1<latitude_to_check && latitude_to_check<0.1&&-0.1<longitude_to_check && longitude_to_check<0.1)
            {LevelPassed();}
            else {Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Not_passed), Toast.LENGTH_SHORT);
                toast_fail.setGravity(Gravity.TOP, 0,100);
                toast_fail.show();}
        });

    }

    // GET lat and Lng of selected point, FROM FRAGMENT!!
    public void get_coordinates(float selected_latitude, float selected_longitude){
        //Toast.makeText(Level5_Activity.this,String.valueOf(selected_latitude) + " , " + String.valueOf(selected_longitude), Toast.LENGTH_LONG).show();
        //GET latitude and longitude
        latitude_to_check = selected_latitude;
        longitude_to_check = selected_longitude;

    /* Get only a selected number of decimal places
        DecimalFormat df = new DecimalFormat("#.000");
        latitude_to_check = Float.parseFloat(df.format(selected_latitude));
        longitude_to_check = Float.parseFloat(df.format(selected_latitude));
    */

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
                Toast.makeText(Level5_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level5_Activity.this, LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level5_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level5_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level5_Activity.this, LevelActivity.class);
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
            Toast.makeText(Level5_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level5_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}