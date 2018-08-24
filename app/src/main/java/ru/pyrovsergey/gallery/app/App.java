package ru.pyrovsergey.gallery.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance;

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }
}
