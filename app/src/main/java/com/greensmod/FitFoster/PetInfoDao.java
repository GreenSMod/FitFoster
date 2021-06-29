package com.greensmod.FitFoster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PetInfoDao {

    @Query("SELECT * FROM pet_info_table")
    List<PetInfo> getAll();

    @Query("SELECT * FROM pet_info_table WHERE id = :id")
    PetInfo getById(int id);

    @Query("DELETE FROM pet_info_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PetInfo petInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(PetInfo petInfo);

    @Delete
    void delete(PetInfo petInfo);

}