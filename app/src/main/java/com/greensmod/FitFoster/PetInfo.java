package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_info_table")
public class PetInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(defaultValue = "0")
    public int steps_points;

    @ColumnInfo(defaultValue = "0")
    public int events_points;

    @ColumnInfo(defaultValue = "1")
    public int texture_id;

    @ColumnInfo(defaultValue = "0")
    public int texture_color_id;

    @ColumnInfo(defaultValue = "0")
    public int costume_id;

    @ColumnInfo(defaultValue = "0")
    public int hat_id;

    @ColumnInfo(defaultValue = "0")
    public int glasses_id;

    @ColumnInfo(defaultValue = "0")
    public int finery_id;

    @ColumnInfo(defaultValue = "0")
    public int body_id;

    @ColumnInfo(defaultValue = "0")
    public int legs_id;

    @ColumnInfo(defaultValue = "0")
    public int shoes_id;

    @ColumnInfo(defaultValue = "0")
    public int background_id;

    @ColumnInfo(defaultValue = "0")
    public int theme_id;

}