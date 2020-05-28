package com.strandfory.ometely;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class WorkBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "Ometely.db";
    static final String TABLE_CONTACTS = "pizzaOrders";
    static final String TABLE_WORKERS = "workers";

    static final String KEY_ID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_NUMBER = "number";
    static final String KEY_ADDRESS = "address";
    static final String KEY_PIZZA1 = "pizza1";
    static final String KEY_PIZZA2 = "pizza2";
    static final String KEY_PIZZA3 = "pizza3";
    static final String KEY_PIZZA4 = "pizza4";
    static final String KEY_PIZZA5 = "pizza5";
    static final String KEY_ISCOOK = "iscook";
    static final String KEY_PRICE = "price";
    static final String KEY_USERNAME = "login";
    static final String KEY_USERPASS = "pass";
    static final String KEY_COOK = "cook";
    static final String KEY_DELIVER = "deliver";
    static final String KEY_MANAGER = "manager";


    WorkBD(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_NUMBER + " TEXT, "+ KEY_ADDRESS + " TEXT, " + KEY_PIZZA1 + " TEXT, " +  KEY_PIZZA2 + " TEXT, " + KEY_PIZZA3 + " TEXT, " + KEY_PIZZA4 + " TEXT, " + KEY_PRICE  +" INTEGER, " + KEY_PIZZA5 + " TEXT, " + KEY_ISCOOK + " BLOB DEFAULT 0 "+ " )");
        db.execSQL("CREATE TABLE " + TABLE_WORKERS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME + " TEXT, " + KEY_USERPASS + " TEXT, " + KEY_COOK + " BLOB DEFAULT 0, " + KEY_DELIVER + " BLOB DEFAULT 0, " + KEY_MANAGER + " BLOB DEFAULT 0 )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKERS);
        onCreate(db);
    }

}
