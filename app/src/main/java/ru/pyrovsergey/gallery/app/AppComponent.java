package ru.pyrovsergey.gallery.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.pyrovsergey.gallery.model.db.DataStorage;
import ru.pyrovsergey.gallery.model.db.DataStorageModule;
import ru.pyrovsergey.gallery.presenter.ListThemeFragmentModule;
import ru.pyrovsergey.gallery.ui.ListThemeFragment;

@Singleton
@Component(modules = {DataStorageModule.class, ListThemeFragmentModule.class})
public interface AppComponent {
    DataStorage getDataStorage();

    ListThemeFragment getListThemeFragment();
}
