package ru.pyrovsergey.gallery.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.db.ContractDataStorage;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

@InjectViewState
public class ListOfSelectedTopicsFragmentPresenter extends MvpPresenter<ListOfSelectedTopicsContract> {
    private ContractDataStorage dataStorage;

    public ListOfSelectedTopicsFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public List<PhotosItem> getPhotosItemList() {
        return dataStorage.getPhotosItems();
    }
}
