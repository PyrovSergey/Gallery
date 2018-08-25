package ru.pyrovsergey.gallery.model;

import java.util.List;

import ru.pyrovsergey.gallery.model.dto.PhotosItem;

public interface SearchPhotosCallback {
    void onSuccessLoad(List<PhotosItem> photos);

    void onErrorLoad(Throwable error);
}
