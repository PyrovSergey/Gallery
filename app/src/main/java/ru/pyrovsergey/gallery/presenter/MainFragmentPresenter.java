package ru.pyrovsergey.gallery.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<MainListView> implements SearchPhotosCallback {
    private ContractDataStorage dataStorage;

    public MainFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public List<ThemeWallpaper> getMainListWallpaper() {
        return dataStorage.getMainListWallpapers();
    }

    public void searchWallpapers(String query) {
        dataStorage.searchWallpapersOnRequest(query, this);
    }

    @Override
    public void onSuccessLoad(List<PhotosItem> photos) {
        for (PhotosItem item : photos) {
            Log.i("MyTAG", item.getSrc().getPortrait());
        }
    }

    @Override
    public void onErrorLoad(Throwable error) {
        Log.i("MyTAG", error.getMessage());
    }
}
