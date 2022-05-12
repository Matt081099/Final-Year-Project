package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class database extends SQLiteOpenHelper{
    public static final String DBNAME = "Exercise.db";
    public database(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table exercise(id INTEGER primary key AUTOINCREMENT,email TEXT, date DATE, exerciseName TEXT, exerciseAmount INTEGER, points INTEGER)");
        MyDB.execSQL("create Table users(email TEXT primary key, fullname TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists exercise");
        MyDB.execSQL("drop Table if exists users");
    }

    public void insertDataExercise(String email, LocalDateTime date, String exerciseName, int exerciseAmount, int points){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("email", email);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("exerciseName", exerciseName);
        contentValues.put("exerciseAmount", exerciseAmount);
        contentValues.put("points", points);
        long result = MyDB.insert("exercise", null, contentValues);
    }

    public Boolean insertDataLogin(String email, String fullname, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("email", email);
        contentValues.put("fullname", fullname);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDate(){
        LocalDate date = LocalDate.now();
    }

    public int totalPoints(){
        int points = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(points) from exercise", null);
        if (cursor.moveToFirst()) points = cursor.getInt(0);
        cursor.close();
        db.close();
        return points;
    }

    public String getTopExercise(){
        String topexercise = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select exerciseName, sum(exerciseAmount) as value_occurrence from exercise group by exerciseName order by value_occurrence desc limit 1",null);

        if (cursor.moveToFirst()) topexercise = cursor.getString(0);
        cursor.close();
        db.close();
        return topexercise;
    }

    // To get total hours. 1 exercise = 3 seconds
    public double totalExerciseAmount(){
        int amount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(exerciseAmount) from exercise", null);

        if (cursor.moveToFirst()) amount = cursor.getInt(0);
        cursor.close();
        db.close();

        return amount;
    }

    public int workoutTotal(){
        int total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select count (distinct date) from exercise", null);

        if (cursor.moveToFirst()) total = cursor.getInt(0);
        cursor.close();
        db.close();

        return total;
    }

    public int getStreak(){
        int streak = 0;
        SQLiteDatabase db = getReadableDatabase();

        db.close();

        return streak;
    }

    public void last7Days(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select amount from exercise where date > datetime('now', '-7 days')", null);

    }


    public String getEmail() {
        String email = "";
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("Select * from users where email = ? and fullname = ?", null);
        if (cursor.moveToFirst()) email = cursor.getString(0);
        cursor.close();
        MyDb.close();

        return email;
    }

    public Boolean checkemailpassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[] {email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getFullName(String fullname){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("Select * from users where email = ? and fullname = ?", new String[] {fullname});
        if (cursor.getCount()>0)
            return fullname;
        else
            return "";
    }





}


