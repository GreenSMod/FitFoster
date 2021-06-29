package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wardrobe.class}, version = 2, exportSchema = false)
public abstract class WardrobeRoomDatabase extends RoomDatabase {
    public abstract WardrobeDao wardrobeDao();
}