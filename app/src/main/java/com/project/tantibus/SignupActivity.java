package com.project.tantibus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText username, password;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.User_Signup_text);
        password = findViewById(R.id.Password_Signup_text);
        DB = new DBHelper(this);

        final Button signup_button = findViewById(R.id.signup_button);
        //BUTTON FOR THE LOGIN
        signup_button.setOnClickListener(view -> {
            //check signup
            String user = username.getText().toString();
            String psw = password.getText().toString();

            if(user.isEmpty() || psw.isEmpty()) {
                Toast.makeText(SignupActivity.this, getResources().getString(R.string.Not_ready), Toast.LENGTH_SHORT).show();
            }
            else{
                Boolean checkUser = DB.checkUsername(user);
                if(!checkUser){
                    Boolean insert = DB.insertData(user,psw);
                    if(insert){
                        //ALL CHECKS ARE GOOD
                        //STORE USER AND LEVEL IN THE GLOBAL CLASS
                        GlobalClass.GlobalUser = user;
                        GlobalClass.GlobalLevel = 1;
                        Toast.makeText(SignupActivity.this, getResources().getString(R.string.Welcome) + ", " + GlobalClass.GlobalUser + "!", Toast.LENGTH_SHORT).show();
                        final Intent LevelActivity = new Intent(SignupActivity.this, LevelActivity.class);
                        startActivity(LevelActivity);
                        finish();
                    }else{
                        Toast.makeText(SignupActivity.this, getResources().getString(R.string.Failed), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.ExistingUser), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}