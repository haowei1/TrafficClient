package com.mad.trafficclient.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";

    public MyDatabaseHelper(Context context) {
        super(context, "account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table account(id integer primary key autoincrement, carId varchar(10), money varchar(5), name " +
                "varchar(10), time varchar(20))");
        Log.e(TAG, "onCreate: create successful");
    }
    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
