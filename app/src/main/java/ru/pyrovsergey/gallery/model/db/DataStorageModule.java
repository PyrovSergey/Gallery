package ru.pyrovsergey.gallery.model.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataStorageModule {
    @Singleton
    @Provides
    DataStorage provideDataStorage() {
        return new DataStorage();
    }
}
