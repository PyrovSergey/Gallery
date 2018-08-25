package ru.pyrovsergey.gallery.model;

import java.util.List;

import ru.pyrovsergey.gallery.model.dto.PhotosItem;

public interface SearchPhotosCallback {
    void onSuccessLoad();

    void onErrorLoad(Throwable error);
}
