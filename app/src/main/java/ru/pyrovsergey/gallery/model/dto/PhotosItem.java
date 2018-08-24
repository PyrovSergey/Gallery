package ru.pyrovsergey.gallery.model.dto;

import com.google.gson.annotations.SerializedName;


public class PhotosItem {

    @SerializedName("src")
    private Src src;

    @SerializedName("width")
    private int width;

    @SerializedName("photographer")
    private String photographer;

    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private int height;

    public void setSrc(Src src) {
        this.src = src;
    }

    public Src getSrc() {
        return src;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return
                "PhotosItem{" +
                        "src = '" + src + '\'' +
                        ",width = '" + width + '\'' +
                        ",photographer = '" + photographer + '\'' +
                        ",id = '" + id + '\'' +
                        ",url = '" + url + '\'' +
                        ",height = '" + height + '\'' +
                        "}";
    }
}