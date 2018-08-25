package ru.pyrovsergey.gallery.presenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pyrovsergey.gallery.ui.ListThemeFragment;

@Module
public class ListThemeFragmentModule {
    @Singleton
    @Provides
    ListThemeFragment provideListThemeFragment() {
        return new ListThemeFragment();
    }
}
