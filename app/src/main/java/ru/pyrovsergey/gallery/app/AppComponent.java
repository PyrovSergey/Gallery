package ru.pyrovsergey.gallery.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.pyrovsergey.gallery.model.db.DataStorage;
import ru.pyrovsergey.gallery.model.db.DataStorageModule;

@Singleton
@Component(modules = {DataStorageModule.class})
public interface AppComponent {
    DataStorage getDataStorage();
}
