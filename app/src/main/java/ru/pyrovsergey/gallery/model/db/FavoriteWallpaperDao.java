package ru.pyrovsergey.gallery.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;

@Dao
public interface FavoriteWallpaperDao {

    @Query("SELECT * FROM favorite_wallpapers")
    Maybe<List<FavoriteWallpaper>> getAll();

    @Insert
    void insert(FavoriteWallpaper favoriteWallpaper);

    @Delete
    void delete(FavoriteWallpaper favoriteWallpaper);

    @Query("SELECT * FROM favorite_wallpapers WHERE id = :id")
    Maybe<FavoriteWallpaper> getById(int id);
}
