package ru.pyrovsergey.gallery.model.db;

import java.util.List;

import ru.pyrovsergey.gallery.model.ThemeWallpapers;

public interface ContractDataStorage {
    List<ThemeWallpapers> getMainListWallpapers();
}
