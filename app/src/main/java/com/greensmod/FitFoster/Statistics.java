package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistics_table", indices = {@Index(value = "label", unique = true)})
public class Statistics {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String label;

    public String description;

    @ColumnInfo(defaultValue = "0")
    public int value;

    //    1 - available to show (if value = 0), -1 - unavailable to show
    public int type_id;
}