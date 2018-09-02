package ru.pyrovsergey.gallery.model.db;

import java.util.List;

import ru.pyrovsergey.gallery.DetailListener;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.presenter.FavoritePresenter;

public interface ContractDataStorage {
    List<ThemeWallpaper> getMainListWallpapers();

    void searchWallpapersOnRequest(String query, SearchPhotosCallback searchPhotosCallback, int numberPage);

    List<PhotosItem> getPhotosItems();

    void isAddedToBookmarks(int id, DetailListener listener);

    void deleteBookmark(FavoriteWallpaper favorite, DetailListener listener);

    void insertBookmark(FavoriteWallpaper favorite, DetailListener listener);

    void requestFavoriteList(FavoriteListener listener);

    List<FavoriteWallpaper> getFavoriteWallpapersList();
}
