package com.project.tantibus.levels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.tantibus.DBHelper;
import com.project.tantibus.GlobalClass;
import com.project.tantibus.LevelActivity;
import com.project.tantibus.R;

import java.util.Random;

public class Level8_Activity extends AppCompatActivity {

    //---LEVEL 8---//
    //---TIME---//

    //final static int current_level = 8;
    final static int next_level = 9;

    DBHelper DB;

    //DEFINE EMPTY PLACE, GRID and others dim
    final static int grid_dim = 4;
    final static int total_places = grid_dim*grid_dim;
    final static int total_tiles = total_places - 1;
    private int emptyX = 3;
    private int emptyY = 3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level8);

        //Button to go back to menu
        final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> LevelMenu());

        //CREATING THE BOARD
        //load the relative layout (just the buttons)
        loadViews();
        //load number of tiles
        loadNumbers();
        //generate a new possible combination
        generateNumbers();
        //if solvable generate the corresponding layout
        loadDataToViews();
    }

    //load the relative layout
    private void loadViews(){
        //get grid group
        group = findViewById(R.id.group);
        //get buttons matrix
        buttons = new Button[grid_dim][grid_dim];
        for (int i=0; i< group.getChildCount(); i++){
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
        }
    }

    //load number of tiles
    private void loadNumbers(){
        tiles = new int[total_tiles];
        for (int i=0; i< group.getChildCount() - 1; i++){
            tiles[i] = i+1;
        }
    }

    //generate a new possible combination
    private void generateNumbers(){
        int n = total_tiles;
        Random random = new Random();
        while (n>1){
            // choose from 0 to ((n--) - 1) if n=15 choose from 0 to 13;
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum]=tiles[n];
            tiles[n] = temp;
        }
        //I want an unsolvable puzzle
        if (isSolvable()){generateNumbers();}
    }

    //check if the layout is solvable
    private boolean isSolvable(){
        int countInversion = 0;
        for (int i=0; i<total_tiles; i++){
            for (int j=0; j < i; j++){
                if(tiles[j]>tiles[i]){
                    countInversion++;
                }
            }
        }
        boolean Solvable;
        Solvable = countInversion % 2 == 0;
        return Solvable;
    }

    //if solvable generate the corresponding layout
    private void  loadDataToViews(){
        emptyX = 3;
        emptyY = 3;
        for (int i=0; i< group.getChildCount() - 1; i++){
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundColor(android.R.drawable.btn_default);
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, R.color.black));
    }

    //move the pushed button IF AND ONLY IF is movable, MADE BY BUTTONS IN LAYOUT
    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0) -'0';
        int y = button.getTag().toString().charAt(1) -'0';
        if((Math.abs(emptyX-x)==1&&emptyY==y)||((emptyX==x)&&(Math.abs(emptyY-y)==1))){
            buttons[emptyX][emptyY].setText((button.getText().toString()));
            buttons[emptyX][emptyY].setBackgroundColor(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            emptyX = x;
            emptyY = y;
            checkWin();
        }
    }

    //check if REVERSED puzzle is solved
    private void checkWin(){
        boolean isWin = false;
        if ((emptyX==(grid_dim-1))&&(emptyY==(grid_dim-1))){
            for (int i=0; i< group.getChildCount()-1; i++){
                if(buttons[i/4][i%4].getText().toString().equals(String.valueOf(total_tiles-i))){
                    isWin = true;
                }else{
                    isWin = false;
                    break;
                }
            }
        }
        if(isWin){
            LevelPassed();
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
                Toast.makeText(Level8_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
                GlobalClass.GlobalLevel = next_level;
                final Intent LevelActivity = new Intent(Level8_Activity.this, com.project.tantibus.LevelActivity.class);
                startActivity(LevelActivity);
                finish();
            }else{
                //eventual error
                Toast.makeText(Level8_Activity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
            }
        }else{
            //this level is not the max level
            Toast.makeText(Level8_Activity.this,getResources().getString(R.string.Level_Passed), Toast.LENGTH_SHORT).show();
            final Intent LevelActivity = new Intent(Level8_Activity.this, LevelActivity.class);
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
            Toast.makeText(Level8_Activity.this,getResources().getString(R.string.back_navigation), Toast.LENGTH_SHORT).show();
        }
        final Intent LevelActivity = new Intent(Level8_Activity.this, LevelActivity.class);
        startActivity(LevelActivity);
        finish();
    }

}