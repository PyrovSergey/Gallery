package ru.pyrovsergey.gallery;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;

@InjectViewState
public class Presenter extends MvpPresenter<MyContract> {
    private ContractDataStorage dataStorage;

    public Presenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public void searchWallpapers(String query) {
        getViewState().startListOfSelectedTopicsFragment(query);
    }

    public void callAboutGallery() {
        getViewState().showAboutGalleryMessage();
    }
}
