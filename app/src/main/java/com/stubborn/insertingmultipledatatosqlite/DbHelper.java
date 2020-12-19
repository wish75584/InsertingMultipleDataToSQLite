package com.stubborn.insertingmultipledatatosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    //factory
    public static final int DATABASE_VERSION = 1;
    //passing databse name
    public static final String  DATABSE_NAME = "sms_details";
    //database
    private static final String CREATING_DATABASE_SMS = "CREATE TABLE SMS (sms_id INTEGER not null PRIMARY KEY AUTOINCREMENT,sms_uuid TEXT not null,sms_number TEXT not null,sms_body TEXT  not null ,sms_time TEXT)";


    public DbHelper(@Nullable Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATING_DATABASE_SMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
