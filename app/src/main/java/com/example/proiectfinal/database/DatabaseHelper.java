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

public class DatabaseHelper extends SQLiteOpenHelper {
   public static final String DATABASE_NAME = "DataBASE.db";
   public static final String TABLE_NAME = "User";
   public static final String COL_1 = "id";
   public static final String COL_2 = "username";
   public static final String COL_3 = "password";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key autoincrement, username text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(
                "select * from " + TABLE_NAME + " where " + COL_2 + " =  '" + username + "' and " + COL_3 + " = '" + password + "'",
                null);
        return res;
    }
}
