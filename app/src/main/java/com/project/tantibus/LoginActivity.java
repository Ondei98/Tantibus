package com.project.tantibus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.User_Login_text);
        password = findViewById(R.id.Password_Login_text);
        DB = new DBHelper(this);

        final Button loadGame_button = findViewById(R.id.login_button);
        loadGame_button.setOnClickListener(view -> {
            //check the login
            String user = username.getText().toString();
            String psw = password.getText().toString();

            if(user.isEmpty() || psw.isEmpty()){
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.Not_ready), Toast.LENGTH_SHORT).show();
            }else{
                Boolean checkUserPsw = DB.checkUsernamePassword(user, psw);
                if(checkUserPsw){
                    //ALL CHECKS ARE GOOD AND UPDATE GLOBAL CLASS
                    GlobalClass.GlobalUser = user;
                    GlobalClass.GlobalLevel = DB.checkLevel(GlobalClass.GlobalUser);
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.Continue) + ", " + GlobalClass.GlobalUser + "!", Toast.LENGTH_SHORT).show();
                    final Intent LevelActivity = new Intent(LoginActivity.this, LevelActivity.class);
                    startActivity(LevelActivity);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.WrongKey), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}