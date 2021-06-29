package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Days.class}, version = 2, exportSchema = false)
public abstract class DaysRoomDatabase extends RoomDatabase {
    public abstract DaysDao daysDao();
}