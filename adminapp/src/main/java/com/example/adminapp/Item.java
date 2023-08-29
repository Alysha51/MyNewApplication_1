package com.example.adminapp;

public class Item {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String category;

    public Item() {
        // Default constructor required for Firebase
    }


    // Call this method to update items when categories are selected
    private void updateSelectedItems() {
        // Update selectedItems based on selectedCategories
    }



    public Item(String id, String title, String description,String category, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getter methods for the attributes
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() { return category;}
}
