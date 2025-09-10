package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

public class Level2_Activity extends AppCompatActivity {

    //---LEVEL 2---//
    //---DIRECTION---//

    //final static int current_level = 2;
    final static int next_level = 3;

    DBHelper DB;

    private SensorManager sensorService;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //call the compass
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        //Button to check level condition
        final Button button_compass = findViewById(R.id.button_compass);
        button_compass.setOnClickListener(view -> {

            //GET AZIMUTH with void
            if (sensor != null) {
                sensorService.registerListener(mySensorEventListener, sensor,SensorManager.SENSOR_DELAY_UI);
            }
        });
    }

    //CHECK IF POINTING THE NORTH
    final private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            // 0=North, pi/2=East, pi=South, pi/2=West
            // 0=North, 180=South, (360 = North)
            float azimuth = event.values[0];
            //STOP QUERYING SENSOR
            sensorService.unregisterListener(mySensorEventListener,sensor);
            //Toast.makeText(Level2_Activity.this,String.valueOf(azimuth), Toast.LENGTH_LONG).show();

            //CHECK NORTH COORDINATE
            if ((azimuth>= 0 && azimuth<5)||(azimuth>= 356 && azimuth<360)) {LevelPassed();}
            else {Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Not_passed), Toast.LENGTH_SHORT);
                toast_fail.setGravity(Gravity.TOP, 0,100);
                toast_fail.show();}

        }
    };

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
                Toast.makeText(Level2_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level2_Activity.this, LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level2_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level2_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level2_Activity.this, LevelActivity.class);
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
            Toast.makeText(Level2_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level2_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }
}