package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WardrobeDao {

    @Query("SELECT * FROM wardrobe_table")
    List<Wardrobe> getAll();

    @Query("SELECT * FROM wardrobe_table WHERE id = :id")
    Wardrobe getById(int id);

    @Query("DELETE FROM wardrobe_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wardrobe wardrobe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Wardrobe wardrobe);

    @Delete
    void delete(Wardrobe wardrobe);

}