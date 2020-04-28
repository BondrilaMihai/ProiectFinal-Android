package com.example.proiectfinal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.proiectfinal.dao.UserDao;
import com.example.proiectfinal.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
