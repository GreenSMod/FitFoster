package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements_table", indices = {@Index(value = "label", unique = true)})
public class Achievements {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String label;

    public String name;

    public String description;

    //    0 - not completed, 1 - completed, -1 - unavailable to complete
    @ColumnInfo(defaultValue = "0")
    public int completed_type_id;

    //    1 - 1 action to complete, 2 - fill the progress to complete, -1 - unavailable to complete
    public int type_id;

}