package com.greensmod.FitFoster;

public class AchievementElement {
    private String label;
    private String name;
    private String description;
    private int completed_type_id;
    private int type_id;

    public AchievementElement(String label, String name, String description, int completed_type_id, int type_id) {
        this.label = label;
        this.name = name;
        this.description = description;
        this.completed_type_id = completed_type_id;
        this.type_id = type_id;
    }

    public String getLabel() {
        return this.label;
    }

//    public void setLabel(String label) {
//        this.label = label;
//    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCompleted_type_id() {
        return this.completed_type_id;
    }

    public int getType_id() {
        return this.type_id;
    }
}
