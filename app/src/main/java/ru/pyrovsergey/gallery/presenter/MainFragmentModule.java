package ru.pyrovsergey.gallery.presenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pyrovsergey.gallery.ui.MainFragment;

@Module
public class MainFragmentModule {
    @Singleton
    @Provides
    MainFragment provideMainFragment() {
        return new MainFragment();
    }
}
