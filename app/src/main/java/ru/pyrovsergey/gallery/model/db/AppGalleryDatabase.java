package ru.pyrovsergey.gallery.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.pyrovsergey.gallery.model.FavoriteWallpaper;

@Database(entities = {FavoriteWallpaper.class}, version = 1, exportSchema = false)
public abstract class AppGalleryDatabase extends RoomDatabase {
    public abstract FavoriteWallpaperDao favoriteWallpaperDao();
}
