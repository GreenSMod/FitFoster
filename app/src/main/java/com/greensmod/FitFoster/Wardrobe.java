package com.greensmod.FitFoster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wardrobe_table")
public class Wardrobe {

    //    1-16 - texture, 17-48 - texture color, 49-176 - costume, 177-304 - hat, 305-432 - glasses,
//    433-496 - finery, 497-688 - body, 689-816 - legs, 817-944 - shoes
//    945-1072 - background, 1073-1136 - theme
    @PrimaryKey
    @ColumnInfo(index = true)
    public long id;

    //    1 - texture, 2 - texture color, 3 - costume, 4 - hat, 5 - glasses,
//    6 - finery, 7 - body, 8 - legs, 9 - shoes
//    21 - background, 22 - theme
    @ColumnInfo(index = true)
    public int type_id;

    public String name;

    //    0 - no cost, 1 - steps points, 2 - events points
    public int points_type_id;

    public int cost;

    //    0 - not bought, 1 - bought, -1 - unavailable to buy
    @ColumnInfo(defaultValue = "0")
    public int bought_type_id;

    //    0 - free, 1 - can be bought, 2 - can be achieved
    public int getting_type_id;

    public int resource_id;

}