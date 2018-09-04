package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.presenters.contracts.DetailViewContract;
import ru.pyrovsergey.gallery.presenters.listeners.DetailListener;

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

    public void noInternetConnection() {
        getViewState().showNoConnectionDialogMessage();
    }

    @Override
    public void positiveResultCheckIsAddToBookmarks() {
        getViewState().positiveResultCheckIsAddToBookmarks();
    }

    @Override
    public void onSuccessDeleteBookmark() {
        getViewState().onSuccessDeleteBookmark();
        getViewState().showToastMessage(App.getInstance().getContext().getString(R.string.removed_from_favorites));
    }

    @Override
    public void onErrorDeleteBookmark() {
        getViewState().showToastMessage(App.getInstance().getContext().getString(R.string.failed_to_delete_from_favorites));
    }

    @Override
    public void onSuccessInsertBookmark() {
        getViewState().onSuccessInsertBookmark();
        getViewState().showToastMessage(App.getInstance().getContext().getString(R.string.added_to_favorites));
    }

    @Override
    public void onErrorInsertBookmark() {
        getViewState().showToastMessage(App.getInstance().getContext().getString(R.string.error_adding_to_favorites));
    }
}
