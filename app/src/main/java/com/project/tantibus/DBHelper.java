package com.project.tantibus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //private static final String TAG_LOG = DBHelper.class.getName();

    //DEFINE DATABASE
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    //CREATION OF DATABASE
    @Override
     public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username text primary key, password text, level integer)");
     }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    //INSERT A NEW USER IN THE DB
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("level", 1);
        long result = MyDB.insert("users", null, contentValues);
        return result != -1;
    }

    //CHECK IF A USER IS ALREADY PRESENT
    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        try (Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username})) {
            return cursor.getCount() > 0;
        }
    }

    //CHECK IF PSW WRITTEN IS CORRECT
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        try (Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password})) {
            return cursor.getCount() > 0;
        }
    }

    //CHECK CURSOR OF CURRENT PLAYER
    public int checkLevel(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        try (Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username})) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
             /* Debug message
            String message = cursor.getString(0);
                Log.d(TAG_LOG, message);
               */
                return cursor.getInt(2);
            } else {
                return -1;
            }
        }
    }

    //UPDATE THE LEVEL
    public Boolean UpdateLevel(String username, int level) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("level", level);
        try (Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username})) {

            if (cursor.getCount() > 0) {
                long result = MyDB.update("users", contentValues, "username=?", new String[]{username});
                return result != -1;
            } else {
                return false;
            }
        }
    }

}

