package com.greensmod.FitFoster;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PetInfo.class}, version = 2, exportSchema = false)
public abstract class PetInfoRoomDatabase extends RoomDatabase {
    public abstract PetInfoDao petInfoDao();
}