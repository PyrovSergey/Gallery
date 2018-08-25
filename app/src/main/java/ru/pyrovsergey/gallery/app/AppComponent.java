package ru.pyrovsergey.gallery.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.pyrovsergey.gallery.model.db.DataStorage;
import ru.pyrovsergey.gallery.model.db.DataStorageModule;
import ru.pyrovsergey.gallery.presenter.MainFragmentModule;
import ru.pyrovsergey.gallery.ui.MainFragment;

@Singleton
@Component(modules = {DataStorageModule.class, MainFragmentModule.class})
public interface AppComponent {
    DataStorage getDataStorage();

    MainFragment getMainFragment();
}
