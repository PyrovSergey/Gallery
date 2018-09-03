package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.presenters.contracts.ListThemeContract;

@InjectViewState
public class ListThemeFragmentPresenter extends MvpPresenter<ListThemeContract> {
    private DataStorageContract dataStorage;

    public ListThemeFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public List<ThemeWallpaper> getMainListWallpaper() {
        return dataStorage.getMainListWallpapers();
    }
}
