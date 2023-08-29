package com.example.adminapp;

public class Category {
    private int iconResId;
    private String name;
    private boolean selected;

    public Category(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
        this.selected = false;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

