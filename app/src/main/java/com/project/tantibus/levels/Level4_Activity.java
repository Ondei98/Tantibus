package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

public class Level4_Activity extends AppCompatActivity {

    //---LEVEL 4---//
    //---BALANCE---//

    //final static int current_level = 4;
    final static int next_level = 5;

    DBHelper DB;

    int move_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

    }

    //void used by buttons in layout!! DON'T DELETE
    public void trisClick(View view){

        Button button = (Button) view;

        //check the number of move
        if(button.getText().toString().isEmpty()){
            if (move_number%2==0){
                //even put a O
                button.setText("O");
            }else{
                //odd put an X
                button.setText("X");
            }
            move_number++;
            if(move_number==9){
                checkWin();
            }
        }

    }

    private void checkWin(){
        //IMPORT BUTTON
        final Button position1_tile = findViewById(R.id.puzzle_tile1);
        final Button position2_tile = findViewById(R.id.puzzle_tile2);
        final Button position3_tile = findViewById(R.id.puzzle_tile3);
        final Button position4_tile = findViewById(R.id.puzzle_tile4);
        final Button position5_tile = findViewById(R.id.puzzle_tile5);
        final Button position6_tile = findViewById(R.id.puzzle_tile6);
        final Button position7_tile = findViewById(R.id.puzzle_tile7);
        final Button position8_tile = findViewById(R.id.puzzle_tile8);
        final Button position9_tile = findViewById(R.id.puzzle_tile9);

        //GET AND CHECK THE POSITION OF THE TILES
        if((position1_tile.getText().toString().equals("O"))&&
                (position3_tile.getText().toString().equals("O"))&&
                (position5_tile.getText().toString().equals("O"))&&
                (position7_tile.getText().toString().equals("O"))&&
                (position9_tile.getText().toString().equals("O"))&&
                (position2_tile.getText().toString().equals("X"))&&
                (position4_tile.getText().toString().equals("X"))&&
                (position6_tile.getText().toString().equals("X"))&&
                (position8_tile.getText().toString().equals("X"))){
            LevelPassed();
        }else{
            //RESTART THE LEVEL AND RESET BOARD
            move_number=0;
            position1_tile.setText("");
            position2_tile.setText("");
            position3_tile.setText("");
            position4_tile.setText("");
            position5_tile.setText("");
            position6_tile.setText("");
            position7_tile.setText("");
            position8_tile.setText("");
            position9_tile.setText("");
            Toast toast_fail = Toast.makeText(getApplicationContext(),getResources().getString(R.string.lv4_wrong), Toast.LENGTH_SHORT);
            toast_fail.setGravity(Gravity.TOP, 0,100);
            toast_fail.show();
        }

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
                Toast.makeText(Level4_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level4_Activity.this, com.project.tantibus.LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level4_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level4_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level4_Activity.this, LevelActivity.class);
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
            Toast.makeText(Level4_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level4_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}