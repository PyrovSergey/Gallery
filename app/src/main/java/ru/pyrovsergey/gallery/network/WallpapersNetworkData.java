package ru.pyrovsergey.gallery.network;

import retrofit2.Call;
import retrofit2.Callback;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.dto.Response;
import ru.pyrovsergey.gallery.presenters.listeners.SearchPhotosListener;

public class WallpapersNetworkData {
    private static final int PER_PAGE = 40;
    private final PexelsApi pexelsApi;

    public WallpapersNetworkData() {
        pexelsApi = App.getApi();
    }

    public void searchWallpapersOnRequest(String query, final SearchPhotosListener searchPhotosListener, int numPage) {
        pexelsApi.searchPhoto(query, PER_PAGE, numPage)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body() != null) {
                            searchPhotosListener.onSuccessLoad(response.body().getPhotos());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        searchPhotosListener.onErrorLoad(t);
                    }
                });
    }
}
