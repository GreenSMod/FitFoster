package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Achievements.class}, version = 2, exportSchema = false)
public abstract class AchievementsRoomDatabase extends RoomDatabase {
    public abstract AchievementsDao achievementsDao();
}