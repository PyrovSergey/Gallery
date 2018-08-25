package ru.pyrovsergey.gallery.presenter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MainListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onClickItem(String theme);
}
