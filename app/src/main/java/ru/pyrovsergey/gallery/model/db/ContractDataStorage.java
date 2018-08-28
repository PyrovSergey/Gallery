package ru.pyrovsergey.gallery.model.db;

import java.util.List;

import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

public interface ContractDataStorage {
    List<ThemeWallpaper> getMainListWallpapers();

    void searchWallpapersOnRequest(String query, SearchPhotosCallback searchPhotosCallback, int numberPage);

    List<PhotosItem> getPhotosItems();
}
