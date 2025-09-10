package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.R;
import com.squareup.seismic.ShakeDetector;

import java.util.Random;

public class Level7_Activity extends AppCompatActivity implements ShakeDetector.Listener{

    //---LEVEL 7---//
    //---CHOSEN---//

    //final static int current_level = 7;
    final static int next_level = 8;

    DBHelper DB;

    int face_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //GET LAYOUT OBJECTS
        final Button button_shuffle = findViewById(R.id.button_shuffle);
        //final ImageView dice_icon = findViewById(R.id.dice_icon);

        //GET SHAKE DETECTOR
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        /* CHECK WIN CONDITION WITH BUTTON
        button_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(face_number==1){
                    LevelPassed();
                }else{Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Not_passed), Toast.LENGTH_SHORT);
                    toast_fail.setGravity(Gravity.TOP, 0,100);
                    toast_fail.show();}
            }
        });

 */

         //CHECK WIN CONDITION CLICKING ON DICE
        final ImageView dice_icon = findViewById(R.id.dice_icon);
        dice_icon.setOnClickListener(view -> {
            if(face_number==1){
                LevelPassed();
            }
        });
    }

    //detection of shake
    @Override
    public void hearShake() {
        final ImageView dice_icon = findViewById(R.id.dice_icon);
        face_number = shuffle(dice_icon);
    }

    //Shuffle the dice and update image
    private int shuffle(ImageView dice_icon){
        Random rand = new Random();
        Animation animRotation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        int random = rand.nextInt(6)+1;
        dice_icon.startAnimation(animRotation);

        //call function to change icon
        setImage(dice_icon, random);

        return random;
    }

    //update image in shuffle
    private void setImage(ImageView dice_icon, int random){

        //change icon with rolled number
        switch (random){
            case 1:
                dice_icon.setImageResource(R.drawable.dice_1);
                break;
            case 2:
                dice_icon.setImageResource(R.drawable.dice_2);
                break;
            case 3:
                dice_icon.setImageResource(R.drawable.dice_3);
                break;
            case 4:
                dice_icon.setImageResource(R.drawable.dice_4);
                break;
            case 5:
                dice_icon.setImageResource(R.drawable.dice_5);
                break;
            case 6:
                dice_icon.setImageResource(R.drawable.dice_6);
                break;
        }
    }

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
                Toast.makeText(Level7_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level7_Activity.this, com.project.tantibus.LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level7_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level7_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level7_Activity.this, com.project.tantibus.LevelActivity.class);
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
            Toast.makeText(Level7_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level7_Activity.this, com.project.tantibus.LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}