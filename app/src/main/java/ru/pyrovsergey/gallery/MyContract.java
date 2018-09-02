package ru.pyrovsergey.gallery;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MyContract extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void startListOfSelectedTopicsFragment(String query);

    @StateStrategyType(SkipStrategy.class)
    void showAboutGalleryMessage();
}
