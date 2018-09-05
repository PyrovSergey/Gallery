package ru.pyrovsergey.gallery.network.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pyrovsergey.gallery.network.WallpapersNetworkData;

@Module
public class WallpapersNetworkDataModule {
    @Singleton
    @Provides
    WallpapersNetworkData provideWallpapersNetworkData() {
        return new WallpapersNetworkData();
    }
}
