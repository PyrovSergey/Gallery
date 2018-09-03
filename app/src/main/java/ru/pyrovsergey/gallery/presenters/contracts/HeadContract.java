package ru.pyrovsergey.gallery.presenters.contracts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface HeadContract extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void startListOfSelectedTopicsFragment(String query);

    @StateStrategyType(SkipStrategy.class)
    void showAboutGalleryMessage();
}
