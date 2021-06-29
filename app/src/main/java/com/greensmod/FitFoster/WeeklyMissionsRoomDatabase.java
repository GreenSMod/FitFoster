package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeeklyMissions.class}, version = 1, exportSchema = false)
public abstract class WeeklyMissionsRoomDatabase extends RoomDatabase {
    public abstract WeeklyMissionsDao weeklyMissionsDao();
}