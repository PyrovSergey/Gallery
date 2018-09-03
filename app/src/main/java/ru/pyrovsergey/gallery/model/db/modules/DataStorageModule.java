package ru.pyrovsergey.gallery.model.db.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pyrovsergey.gallery.model.db.DataStorage;

@Module
public class DataStorageModule {
    @Singleton
    @Provides
    DataStorage provideDataStorage() {
        return new DataStorage();
    }
}
