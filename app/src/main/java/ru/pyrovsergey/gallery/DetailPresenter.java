package ru.pyrovsergey.gallery;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> implements DetailListener {
    private ContractDataStorage dataStorage;

    public DetailPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    void isAddToBookmarks(int id) {
        dataStorage.isAddedToBookmarks(id, this);
    }

    void deleteBookmark(FavoriteWallpaper favorite) {
        dataStorage.deleteBookmark(favorite, this);
    }

    void insertBookmark(FavoriteWallpaper favoriteWallpaper) {
        dataStorage.insertBookmark(favoriteWallpaper, this);
    }

    void onClickDownloadWallpaper() {
        getViewState().downloadWallpaper();
    }

    void onClickShare() {
        getViewState().share();
    }

    void onClickSetWallpaper() {
        getViewState().showSetWallpaperAlertMessage();
    }

    void setWallpaper() {
        getViewState().setWallpaper();
    }

    void checkStoragePermission() {
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
