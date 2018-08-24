package ru.pyrovsergey.gallery.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.ThemeWallpapers;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;

@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<MainListView> {
    private ContractDataStorage dataStorage;

    public MainFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public List<ThemeWallpapers> getMainListWallpaper() {
        return dataStorage.getMainListWallpapers();
    }
}
