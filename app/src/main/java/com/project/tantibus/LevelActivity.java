package com.project.tantibus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.tantibus.levels.Level1_Activity;
import com.project.tantibus.levels.Level2_Activity;
import com.project.tantibus.levels.Level3_Activity;
import com.project.tantibus.levels.Level4_Activity;
import com.project.tantibus.levels.Level5_Activity;
import com.project.tantibus.levels.Level6_Activity;
import com.project.tantibus.levels.Level7_Activity;
import com.project.tantibus.levels.Level8_Activity;
import com.project.tantibus.levels.Level9_Activity;
import com.project.tantibus.levels.Level_Dark_Activity;


public class LevelActivity extends AppCompatActivity {

  //private static final int LEVEL = 9;
    private static final String TAG_LOG = LevelActivity.class.getName();

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //IMPORT BUTTON
        final Button level1_button = findViewById(R.id.button_level1);
        final Button level2_button = findViewById(R.id.button_level2);
        final Button level3_button = findViewById(R.id.button_level3);
        final Button level4_button = findViewById(R.id.button_level4);
        final Button level5_button = findViewById(R.id.button_level5);
        final Button level6_button = findViewById(R.id.button_level6);
        final Button level7_button = findViewById(R.id.button_level7);
        final Button level8_button = findViewById(R.id.button_level8);
        final Button level9_button = findViewById(R.id.button_level9);
        //CROWN AND SECRET LEVEL
        final Button levelDark_button = findViewById(R.id.crown_image);

        //CHECK CURRENT LEVEL AND SHOW BUTTONS
        DB = new DBHelper(this);

        int checkLevel = DB.checkLevel(GlobalClass.GlobalUser);

        if(checkLevel>0){level1_button.setVisibility(View.VISIBLE);}
        if(checkLevel>1){level2_button.setVisibility(View.VISIBLE);}
        if(checkLevel>2){level3_button.setVisibility(View.VISIBLE);}
        if(checkLevel>3){level4_button.setVisibility(View.VISIBLE);}
        if(checkLevel>4){level5_button.setVisibility(View.VISIBLE);}
        if(checkLevel>5){level6_button.setVisibility(View.VISIBLE);}
        if(checkLevel>6){level7_button.setVisibility(View.VISIBLE);}
        if(checkLevel>7){level8_button.setVisibility(View.VISIBLE);}
        if(checkLevel>8){level9_button.setVisibility(View.VISIBLE);}
        if(checkLevel>9){levelDark_button.setVisibility(View.VISIBLE);}


        //LEVEL 1 BUTTON
        level1_button.setOnClickListener(view -> openLevel1());
        //LEVEL 2 BUTTON
        level2_button.setOnClickListener(view -> openLevel2());
        //LEVEL 3 BUTTON
        level3_button.setOnClickListener(view -> openLevel3());
        //LEVEL 4 BUTTON
        level4_button.setOnClickListener(view -> openLevel4());
        //LEVEL 5 BUTTON
        level5_button.setOnClickListener(view -> openLevel5());
        //LEVEL 6 BUTTON
        level6_button.setOnClickListener(view -> openLevel6());
        //LEVEL 7 BUTTON
        level7_button.setOnClickListener(view -> openLevel7());
        //LEVEL 8 BUTTON
        level8_button.setOnClickListener(view -> openLevel8());
        //LEVEL 9 BUTTON
        level9_button.setOnClickListener(view -> openLevel9());
        //LEVEL DARK BUTTON
        levelDark_button.setOnClickListener(view -> openLevelDark());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_level);

        //IMPORT BUTTON
        final Button level1_button = findViewById(R.id.button_level1);
        final Button level2_button = findViewById(R.id.button_level2);
        final Button level3_button = findViewById(R.id.button_level3);
        final Button level4_button = findViewById(R.id.button_level4);
        final Button level5_button = findViewById(R.id.button_level5);
        final Button level6_button = findViewById(R.id.button_level6);
        final Button level7_button = findViewById(R.id.button_level7);
        final Button level8_button = findViewById(R.id.button_level8);
        final Button level9_button = findViewById(R.id.button_level9);

        //CROWN AND SECRET LEVEL
        final Button levelDark_button = findViewById(R.id.crown_image);

        //CHECK CURRENT LEVEL AND SHOW BUTTONS
        if(GlobalClass.GlobalLevel>0){level1_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>1){level2_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>2){level3_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>3){level4_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>4){level5_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>5){level6_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>6){level7_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>7){level8_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>8){level9_button.setVisibility(View.VISIBLE);}
        if(GlobalClass.GlobalLevel>9){levelDark_button.setVisibility(View.VISIBLE);}

        //LEVEL 1 BUTTON
        level1_button.setOnClickListener(view -> openLevel1());
        //LEVEL 2 BUTTON
        level2_button.setOnClickListener(view -> openLevel2());
        //LEVEL 3 BUTTON
        level3_button.setOnClickListener(view -> openLevel3());
        //LEVEL 4 BUTTON
        level4_button.setOnClickListener(view -> openLevel4());
        //LEVEL 5 BUTTON
        level5_button.setOnClickListener(view -> openLevel5());
        //LEVEL 6 BUTTON
        level6_button.setOnClickListener(view -> openLevel6());
        //LEVEL 7 BUTTON
        level7_button.setOnClickListener(view -> openLevel7());
        //LEVEL 8 BUTTON
        level8_button.setOnClickListener(view -> openLevel8());
        //LEVEL 9 BUTTON
        level9_button.setOnClickListener(view -> openLevel9());
        //LEVEL DARK BUTTON
        levelDark_button.setOnClickListener(view -> openLevelDark());
    }

    //VOID LEVEL 1
    private void openLevel1()
    {
        Log.d(TAG_LOG, "Access level 1");
        final Intent Level1Activity = new Intent(LevelActivity.this, Level1_Activity.class);
        startActivity(Level1Activity);
        finish();
    }
    //VOID LEVEL 2
    private void openLevel2() {
        Log.d(TAG_LOG, "Access level 2");
        final Intent Level2Activity = new Intent(LevelActivity.this, Level2_Activity.class);
        startActivity(Level2Activity);
        finish();
    }
    //VOID LEVEL 3
    private void openLevel3() {
        Log.d(TAG_LOG, "Access level 3");
        final Intent Level3Activity = new Intent(LevelActivity.this, Level3_Activity.class);
        startActivity(Level3Activity);
        finish();
    }
    //VOID LEVEL 4
    private void openLevel4() {
        Log.d(TAG_LOG, "Access level 4");
        final Intent Level4Activity = new Intent(LevelActivity.this, Level4_Activity.class);
        startActivity(Level4Activity);
        finish();
    }
    //VOID LEVEL 5
    private void openLevel5() {
        Log.d(TAG_LOG, "Access level 5");
        final Intent Level5Activity = new Intent(LevelActivity.this, Level5_Activity.class);
        startActivity(Level5Activity);
        finish();
    }
    //VOID LEVEL 6
    private void openLevel6() {
        Log.d(TAG_LOG, "Access level 6");
        final Intent Level6Activity = new Intent(LevelActivity.this, Level6_Activity.class);
        startActivity(Level6Activity);
        finish();
    }
    //VOID LEVEL 7
    private void openLevel7() {
        Log.d(TAG_LOG, "Access level 7");
        final Intent Level7Activity = new Intent(LevelActivity.this, Level7_Activity.class);
        startActivity(Level7Activity);
        finish();
    }
    //VOID LEVEL 8
    private void openLevel8() {
        Log.d(TAG_LOG, "Access level 8");
        final Intent Level8Activity = new Intent(LevelActivity.this, Level8_Activity.class);
        startActivity(Level8Activity);
        finish();
    }
    //VOID LEVEL 9
    private void openLevel9() {
        Log.d(TAG_LOG, "Access level 9");
        final Intent Level9Activity = new Intent(LevelActivity.this, Level9_Activity.class);
        startActivity(Level9Activity);
        finish();
    }
    //VOID SECRET LEVEL
    private void openLevelDark() {
        Log.d(TAG_LOG, "Access level secret");
        final Intent LevelDarkActivity = new Intent(LevelActivity.this, Level_Dark_Activity.class);
        startActivity(LevelDarkActivity);
        finish();
    }

}