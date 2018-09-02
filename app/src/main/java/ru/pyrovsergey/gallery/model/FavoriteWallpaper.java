package ru.pyrovsergey.gallery.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorite_wallpapers")
public class FavoriteWallpaper implements Parcelable {

    @PrimaryKey
    private int id;

    private String smallUrl;

    private String url;

    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.smallUrl);
        dest.writeString(this.url);
        dest.writeString(this.author);
    }

    public FavoriteWallpaper() {
    }

    protected FavoriteWallpaper(Parcel in) {
        this.id = in.readInt();
        this.smallUrl = in.readString();
        this.url = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<FavoriteWallpaper> CREATOR = new Parcelable.Creator<FavoriteWallpaper>() {
        @Override
        public FavoriteWallpaper createFromParcel(Parcel source) {
            return new FavoriteWallpaper(source);
        }

        @Override
        public FavoriteWallpaper[] newArray(int size) {
            return new FavoriteWallpaper[size];
        }
    };
}
