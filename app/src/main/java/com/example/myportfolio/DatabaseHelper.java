package com.example.myportfolio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "portfolioDatabase";
    public static final String PORTFOLIO_TABLE_NAME = "portfolioDetails";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "create table " + PORTFOLIO_TABLE_NAME + "(id INTEGER PRIMARY KEY, Name text, Email text, Profession text, Work text, Education text, Volunteer text, Activity text, Skill text, Language text)"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PORTFOLIO_TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String name, String email, String profession, String work, String education, String volunteer, String activity, String skill, String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", name);
        contentValues.put("Email", email);
        contentValues.put("Profession", profession);
        contentValues.put("Work", work);
        contentValues.put("Education", education);
        contentValues.put("Volunteer", volunteer);
        //contentValues.put("Volunteer", volunteer);
        contentValues.put("Activity", activity);
        contentValues.put("Skill", skill);
        contentValues.put("Language", language);
        db.replace(PORTFOLIO_TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor fetch(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =db.rawQuery("Select * from "+ PORTFOLIO_TABLE_NAME,null);
        if(resultSet != null)
            resultSet.moveToLast();

        return resultSet;
    }
}
