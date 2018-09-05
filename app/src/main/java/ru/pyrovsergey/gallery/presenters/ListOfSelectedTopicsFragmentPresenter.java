package ru.pyrovsergey.gallery.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.network.WallpapersNetworkData;
import ru.pyrovsergey.gallery.presenters.contracts.ListOfSelectedTopicsContract;
import ru.pyrovsergey.gallery.presenters.listeners.SearchPhotosListener;

@InjectViewState
public class ListOfSelectedTopicsFragmentPresenter extends MvpPresenter<ListOfSelectedTopicsContract> implements SearchPhotosListener {
    private DataStorageContract dataStorage;
    private WallpapersNetworkData networkData;

    public ListOfSelectedTopicsFragmentPresenter() {
        dataStorage = App.getComponent().getDataStorage();
        networkData = App.getComponent().getWallpapersNetworkData();
    }

    public void searchWallpapers(String query, int numberPage) {
        networkData.searchWallpapersOnRequest(query, this, numberPage);
    }

    public List<PhotosItem> getPhotosItemList() {
        return dataStorage.getPhotosItems();
    }

    @Override
    public void onSuccessLoad(List<PhotosItem> list) {
        dataStorage.setPhotoItemList(list);
        getViewState().adapterNotifyDataSetChanged();
    }

    @Override
    public void onErrorLoad(Throwable error) {
        getViewState().onErrorLoadOfLastPage();
    }
}
