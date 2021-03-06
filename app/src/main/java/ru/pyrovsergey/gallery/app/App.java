package ru.pyrovsergey.gallery.app;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.pyrovsergey.gallery.network.PexelsApi;
import ru.pyrovsergey.gallery.model.db.AppGalleryDatabase;

public class App extends Application {
    private static App instance;
    private static AppComponent component;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static PexelsApi api;
    private final static String BASE_URL = "https://api.pexels.com";
    private final static String KEY = "";
    private static AppGalleryDatabase database;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        component = DaggerAppComponent.create();
        context = getApplicationContext();
        database = Room.databaseBuilder(context, AppGalleryDatabase.class, "database").build();
        okHttpClient = getOkHttpClient();
        retrofit = getRetrofitClient(BASE_URL, okHttpClient);
        api = retrofit.create(PexelsApi.class);
    }

    public static boolean isInternetAvailable() {
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConMgr != null
                && mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected();
    }

    private OkHttpClient getOkHttpClient() {
        okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        KEY);

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
        return okHttpClient;
    }

    private static Retrofit getRetrofitClient(String baseUrl, OkHttpClient okHttpClient) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static App getInstance() {
        return instance;
    }

    public static AppComponent getComponent() {
        return component;
    }

    public Context getContext() {
        return context;
    }

    public static PexelsApi getApi() {
        return api;
    }

    public static AppGalleryDatabase getDatabase() {
        return database;
    }
}
