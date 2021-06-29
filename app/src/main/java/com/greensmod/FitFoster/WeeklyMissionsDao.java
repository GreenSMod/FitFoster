package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeeklyMissionsDao {

    @Query("SELECT * FROM weekly_missions_table")
    List<WeeklyMissions> getAll();

    @Query("SELECT * FROM weekly_missions_table WHERE id = :id")
    WeeklyMissions getById(int id);

    @Query("SELECT * FROM weekly_missions_table WHERE date = :date")
    WeeklyMissions getByDate(int date);

    @Query("DELETE FROM weekly_missions_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeeklyMissions weeklyMissions);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(WeeklyMissions weeklyMissions);

    @Delete
    void delete(WeeklyMissions weeklyMissions);

}