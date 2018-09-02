package ru.pyrovsergey.gallery;

public interface DetailListener {
    void positiveResultCheckIsAddToBookmarks();

    void onSuccessDeleteBookmark();

    void onErrorDeleteBookmark();

    void onSuccessInsertBookmark();

    void onErrorInsertBookmark();
}
