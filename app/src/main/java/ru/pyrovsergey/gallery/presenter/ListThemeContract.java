package ru.pyrovsergey.gallery.presenter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ListThemeContract extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onShowMessage(String message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void startListOfSelectedTopicsAdapter();
}
