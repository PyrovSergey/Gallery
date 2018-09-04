package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.presenters.contracts.HeadContract;

@InjectViewState
public class HeadPresenter extends MvpPresenter<HeadContract> {
    private DataStorageContract dataStorage;

    public HeadPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public void searchWallpapers(String query) {
        getViewState().startListOfSelectedTopicsFragment(query);
    }

    public void callAboutGallery() {
        getViewState().showAboutGalleryMessage();
    }

    public void noInternetConnection() {
        getViewState().showNoConnectionDialogMessage();
    }
}
