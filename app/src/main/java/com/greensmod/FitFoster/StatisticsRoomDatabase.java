package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Statistics.class}, version = 2, exportSchema = false)
public abstract class StatisticsRoomDatabase extends RoomDatabase {
    public abstract StatisticsDao statisticsDao();
}