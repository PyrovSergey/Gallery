package ru.pyrovsergey.gallery.model.db.contracts;

import java.util.List;

import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.presenters.listeners.DetailListener;
import ru.pyrovsergey.gallery.presenters.listeners.FavoriteListener;

public interface DataStorageContract {
    List<ThemeWallpaper> getMainListWallpapers();

    List<PhotosItem> getPhotosItems();

    void setPhotoItemList(List<PhotosItem> list);

    void isAddedToBookmarks(int id, DetailListener listener);

    void deleteBookmark(FavoriteWallpaper favorite, DetailListener listener);

    void insertBookmark(FavoriteWallpaper favorite, DetailListener listener);

    void requestFavoriteList(FavoriteListener listener);

    List<FavoriteWallpaper> getFavoriteWallpapersList();
}
