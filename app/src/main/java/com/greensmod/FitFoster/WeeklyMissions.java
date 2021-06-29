package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weekly_missions_table")
public class WeeklyMissions {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public int date;

    public int day_of_year;

    @ColumnInfo(defaultValue = "0")
    public int weekly_heart_points;

    @ColumnInfo(defaultValue = "0")
    public int previous_heart_points;

    @ColumnInfo(defaultValue = "0")
    public int weekly_completed_daily_missions;

    //    1 - not completed, 2 - completed
    @ColumnInfo(defaultValue = "111")
    public int completed_weekly_missions;

}