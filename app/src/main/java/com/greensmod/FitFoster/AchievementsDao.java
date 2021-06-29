package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AchievementsDao {

    @Query("SELECT * FROM achievements_table")
    List<Achievements> getAll();

    @Query("SELECT * FROM achievements_table WHERE id = :id")
    Achievements getById(int id);

    @Query("SELECT * FROM achievements_table WHERE label = :label")
    Achievements getByLabel(String label);

    @Query("DELETE FROM achievements_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievements achievements);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Achievements achievements);

    @Delete
    void delete(Achievements achievements);

}