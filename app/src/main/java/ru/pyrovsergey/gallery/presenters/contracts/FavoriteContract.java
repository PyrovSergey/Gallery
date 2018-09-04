package ru.pyrovsergey.gallery.presenters.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface FavoriteContract extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void adapterNotifyDataSetChanged();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideTextEmptyList();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTextEmptyList();
}
