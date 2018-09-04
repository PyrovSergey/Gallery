package ru.pyrovsergey.gallery.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.pyrovsergey.gallery.fragments.ListThemeFragment;
import ru.pyrovsergey.gallery.fragments.modules.ListThemeFragmentModule;
import ru.pyrovsergey.gallery.model.db.DataStorage;
import ru.pyrovsergey.gallery.model.db.modules.DataStorageModule;

@Singleton
@Component(modules = {DataStorageModule.class, ListThemeFragmentModule.class})
public interface AppComponent {
    DataStorage getDataStorage();

    ListThemeFragment getListThemeFragment();
}
