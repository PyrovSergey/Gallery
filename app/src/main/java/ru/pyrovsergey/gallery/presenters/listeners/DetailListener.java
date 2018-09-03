package ru.pyrovsergey.gallery.presenters.listeners;

public interface DetailListener {
    void positiveResultCheckIsAddToBookmarks();

    void onSuccessDeleteBookmark();

    void onErrorDeleteBookmark();

    void onSuccessInsertBookmark();

    void onErrorInsertBookmark();
}
