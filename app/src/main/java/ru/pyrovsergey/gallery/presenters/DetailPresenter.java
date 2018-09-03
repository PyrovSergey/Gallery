package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.pyrovsergey.gallery.presenters.listeners.DetailListener;
import ru.pyrovsergey.gallery.presenters.contracts.DetailViewContract;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailViewContract> implements DetailListener {
    private DataStorageContract dataStorage;

    public DetailPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public void isAddToBookmarks(int id) {
        dataStorage.isAddedToBookmarks(id, this);
    }

    public void deleteBookmark(FavoriteWallpaper favorite) {
        dataStorage.deleteBookmark(favorite, this);
    }

    public void insertBookmark(FavoriteWallpaper favoriteWallpaper) {
        dataStorage.insertBookmark(favoriteWallpaper, this);
    }

    public void onClickDownloadWallpaper() {
        getViewState().downloadWallpaper();
    }

    public void onClickShare() {
        getViewState().share();
    }

    public void onClickSetWallpaper() {
        getViewState().showSetWallpaperAlertMessage();
    }

    public void setWallpaper() {
        getViewState().setWallpaper();
    }

    public void checkStoragePermission() {
        getViewState().checkStoragePermissionGrantedAndDownload();
    }

    @Override
    public void positiveResultCheckIsAddToBookmarks() {
        getViewState().positiveResultCheckIsAddToBookmarks();
    }

    @Override
    public void onSuccessDeleteBookmark() {
        getViewState().onSuccessDeleteBookmark();
        getViewState().showToastMessage("Wallpaper removed from bookmarks");
    }

    @Override
    public void onErrorDeleteBookmark() {
        getViewState().showToastMessage("Error removing from bookmarks");
    }

    @Override
    public void onSuccessInsertBookmark() {
        getViewState().onSuccessInsertBookmark();
        getViewState().showToastMessage("Wallpaper insert in bookmarks");
    }

    @Override
    public void onErrorInsertBookmark() {
        getViewState().showToastMessage("Error adding to bookmarks");
    }
}
