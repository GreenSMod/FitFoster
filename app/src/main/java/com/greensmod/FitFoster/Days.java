package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "days_table", indices = {@Index(value = "date", unique = true)})
public class Days {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int date;

    @ColumnInfo(defaultValue = "0")
    public int steps;

    @ColumnInfo(defaultValue = "0")
    public int previous_steps;

    @ColumnInfo(defaultValue = "0")
    public int heart_points;

    @ColumnInfo(defaultValue = "0")
    public int calories;

    @ColumnInfo(defaultValue = "0")
    public float distance;

    @ColumnInfo(defaultValue = "0")
    public int time;

    //    1 - not completed, 2 - completed
    @ColumnInfo(defaultValue = "1111")
    public int completed_daily_missions;

}