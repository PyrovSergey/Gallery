package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.presenters.contracts.ListOfSelectedTopicsContract;

@InjectViewState
public class ListOfSelectedTopicsFragmentPresenter extends MvpPresenter<ListOfSelectedTopicsContract> implements SearchPhotosCallback {
    private DataStorageContract dataStorage;

    public ListOfSelectedTopicsFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
    }

    public void searchWallpapers(String query, int numberPage) {
        dataStorage.searchWallpapersOnRequest(query, this, numberPage);
    }

    public List<PhotosItem> getPhotosItemList() {
        return dataStorage.getPhotosItems();
    }

    @Override
    public void onSuccessLoad() {
        getViewState().adapterNotifyDataSetChanged();
    }

    @Override
    public void onErrorLoad(Throwable error) {
        getViewState().onErrorLoadOfLastPage();
    }
}
