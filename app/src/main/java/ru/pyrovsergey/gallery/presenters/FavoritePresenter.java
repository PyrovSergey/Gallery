package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.presenters.contracts.FavoriteContract;
import ru.pyrovsergey.gallery.presenters.listeners.FavoriteListener;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> implements FavoriteListener {
    private DataStorageContract dataStorage;

    public FavoritePresenter() {
        this.dataStorage = App.getComponent().getDataStorage();
    }

    public List<FavoriteWallpaper> getFavoriteWallpapersList() {
        List<FavoriteWallpaper> list = dataStorage.getFavoriteWallpapersList();
        if (list.size() == 0) {
            getViewState().showTextEmptyList();
        } else {
            getViewState().hideTextEmptyList();
        }
        return list;
    }

    public void requestFavoriteList() {
        dataStorage.requestFavoriteList(this);
    }

    @Override
    public void onSuccess() {
        getViewState().adapterNotifyDataSetChanged();
    }
}
