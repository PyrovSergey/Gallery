package ru.pyrovsergey.gallery.presenters.listeners;

import java.util.List;

import ru.pyrovsergey.gallery.model.dto.PhotosItem;

public interface SearchPhotosListener {
    void onSuccessLoad(List<PhotosItem> list);

    void onErrorLoad(Throwable error);
}
