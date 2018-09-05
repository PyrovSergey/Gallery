package ru.pyrovsergey.gallery.presenters.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ListOfSelectedTopicsContract extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void adapterNotifyDataSetChanged();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onErrorLoadOfLastPage();
}
