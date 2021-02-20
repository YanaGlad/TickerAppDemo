package com.example.mynasaapp.models;

public class ScienceI {
    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;

    public ScienceI() {
        title = null;
        subtitle = null;
        description = null;
        imageUrl = null;
    }

    public String getTitle() {
        return title;
    }

    public ScienceI setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ScienceI setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ScienceI setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ScienceI setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


}
