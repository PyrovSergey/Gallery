package ru.pyrovsergey.gallery.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;
import ru.pyrovsergey.gallery.model.db.FavoriteListener;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteContract> implements FavoriteListener {
    private ContractDataStorage dataStorage;

    public FavoritePresenter() {
        this.dataStorage = App.getComponent().getDataStorage();
    }

    public List<FavoriteWallpaper> getFavoriteWallpapersList() {
        return dataStorage.getFavoriteWallpapersList();
    }

    public void requestFavoriteList(){
        dataStorage.requestFavoriteList(this);
    }

    @Override
    public void onSuccess() {
        getViewState().adapterNotifyDataSetChanged();
    }

    public void update() {
        getViewState().adapterNotifyDataSetChanged();
    }
}
