package ru.pyrovsergey.gallery.presenters.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface DetailViewContract extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showSuccessToastMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showErrorToastMessage(String message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void positiveResultCheckIsAddToBookmarks();

    @StateStrategyType(SkipStrategy.class)
    void onSuccessDeleteBookmark();

    @StateStrategyType(SkipStrategy.class)
    void onSuccessInsertBookmark();

    @StateStrategyType(SkipStrategy.class)
    void downloadWallpaper();

    @StateStrategyType(SkipStrategy.class)
    void share();

    @StateStrategyType(SkipStrategy.class)
    void checkStoragePermissionGrantedAndDownload();

    @StateStrategyType(SkipStrategy.class)
    void showSetWallpaperAlertMessage();

    @StateStrategyType(SkipStrategy.class)
    void setWallpaper();

    @StateStrategyType(SkipStrategy.class)
    void showNoConnectionDialogMessage();
}
