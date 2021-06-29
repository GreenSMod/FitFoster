package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StatisticsDao {

    @Query("SELECT * FROM statistics_table")
    List<Statistics> getAll();

    @Query("SELECT * FROM statistics_table WHERE id = :id")
    Statistics getById(int id);

    @Query("SELECT * FROM statistics_table WHERE label = :label")
    Statistics getByLabel(String label);

    @Query("DELETE FROM statistics_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Statistics statistics);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Statistics statistics);

    @Delete
    void delete(Statistics statistics);

}