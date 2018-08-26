package ru.pyrovsergey.gallery.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;

@InjectViewState
public class ListThemeFragmentPresenter extends MvpPresenter<ListThemeContract> implements SearchPhotosCallback {
    private ContractDataStorage dataStorage;

    public ListThemeFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public List<ThemeWallpaper> getMainListWallpaper() {
        return dataStorage.getMainListWallpapers();
    }

    public void searchWallpapers(String query) {
        dataStorage.searchWallpapersOnRequest(query, this);
    }

    @Override
    public void onSuccessLoad() {
        getViewState().startListOfSelectedTopicsAdapter();
    }

    @Override
    public void onErrorLoad(Throwable error) {
        getViewState().onShowMessage(error.getMessage());
    }
}
