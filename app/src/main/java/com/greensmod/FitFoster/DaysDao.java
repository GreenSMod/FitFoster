package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaysDao {

    @Query("SELECT * FROM days_table")
    List<Days> getAll();

    @Query("SELECT * FROM days_table WHERE id = :id")
    Days getById(int id);

    @Query("SELECT * FROM days_table WHERE date = :date")
    Days getByDate(int date);

    @Query("DELETE FROM days_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Days days);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Days days);

    @Delete
    void delete(Days days);

}