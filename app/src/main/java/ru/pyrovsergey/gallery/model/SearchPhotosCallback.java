package ru.pyrovsergey.gallery.model;

public interface SearchPhotosCallback {
    void onSuccessLoad();

    void onErrorLoad(Throwable error);
}
