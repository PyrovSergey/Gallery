package ru.pyrovsergey.gallery.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.pyrovsergey.gallery.model.PexelsApi;

public class App extends Application {
    private static App instance;
    private static AppComponent component;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static PexelsApi api;
    private final static String BASE_URL = "https://api.pexels.com";
    private final static String KEY = "";

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.create();
        context = getApplicationContext();
        okHttpClient = getOkHttpClient();
        retrofit = getRetrofitClient(BASE_URL, okHttpClient);
        api = retrofit.create(PexelsApi.class);
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
}
