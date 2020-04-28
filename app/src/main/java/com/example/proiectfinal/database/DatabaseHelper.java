package com.example.proiectfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
   public static final String DATABASE_NAME = "DataBASE.db";
   public static final String USER_TABLE_NAME = "User";
   public static final String COL_1 = "id";
   public static final String USER_COL_2 = "username";
   public static final String USER_COL_3 = "password";

    public static final String EVENT_TABLE_NAME = "Event";
    public static final String EVENT_COL_2 = "title";
    public static final String EVENT_COL_3 = "image";

    private List<String> titles = Arrays.asList(
            "Deschidere La Cascada",
            "Intalnire cu presedintele",
            "Super expozitia",
            "Petrecere in padure",
            "La nunta",
            "Concurs de tuns",
            "1 Mai - Vama Veche",
            "Tur Asia",
            "Parada 1 Decembrie",
            "Plimbare in padure");
    private List<String> images = Arrays.asList(
            "https://i.imgur.com/ZcLLrkY.jpg",
            "https://i.redd.it/w0yp9n73aru41.jpg",
            "https://i.redd.it/sicbz4e79kv41.jpg",
            "https://i.redd.it/8m5kkst2wgv41.jpg",
            "https://i.redd.it/tvwee5a52fv41.png",
            "https://i.redd.it/x580b2plgkv41.jpg",
            "https://i.redd.it/0sy018q1hhv41.jpg",
            "https://i.redd.it/xdu22dp8tjv41.jpg",
            "https://i.redd.it/lpze2x5vklv41.jpg",
            "https://i.redd.it/14pr1v5nwlv41.jpg");


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + " (id integer primary key autoincrement, username text, password text)");
        db.execSQL("create table " + EVENT_TABLE_NAME + " (id integer primary key autoincrement, title text, image text)");

        for(int i = 0 ; i < titles.size() ; i++) {
            db.execSQL("INSERT INTO " + EVENT_TABLE_NAME + " (title,image) VALUES( '" + titles.get(i) + "','" + images.get(i) +"')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);
        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(
                "select * from " + USER_TABLE_NAME + " where " + USER_COL_2 + " =  '" + username + "' and " + USER_COL_3 + " = '" + password + "'",
                null);
        return res;
    }

    public Cursor getAllEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery("select * from " + EVENT_TABLE_NAME, null);
        return res;
    }
}
