package ru.pyrovsergey.gallery.model;

public class ThemeWallpapers {
    private String title;

    private int image;

    public ThemeWallpapers(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
