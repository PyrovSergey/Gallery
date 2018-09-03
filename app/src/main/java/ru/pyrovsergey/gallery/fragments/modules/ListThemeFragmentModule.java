package ru.pyrovsergey.gallery.fragments.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pyrovsergey.gallery.fragments.ListThemeFragment;

@Module
public class ListThemeFragmentModule {
    @Singleton
    @Provides
    ListThemeFragment provideListThemeFragment() {
        return new ListThemeFragment();
    }
}
