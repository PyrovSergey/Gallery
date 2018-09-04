package ru.pyrovsergey.gallery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.fragments.adapters.FavoriteAdapter;
import ru.pyrovsergey.gallery.presenters.FavoritePresenter;
import ru.pyrovsergey.gallery.presenters.contracts.FavoriteContract;

public class FavoriteFragment extends MvpAppCompatFragment implements FavoriteContract, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String SETTING_GALLERY_LAYOUT = "setting_gallery_layout";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private GridLayoutManager layoutManager;
    private SharedPreferences preferences;
    private TextView textViewEmpty;

    @InjectPresenter
    FavoritePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.requestFavoriteList();
        log("onCreate");
    }

    public static FavoriteFragment getInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_list, container, false);
        progressBar = view.findViewById(R.id.choice_list_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        textViewEmpty = view.findViewById(R.id.choice_list_text);
        textViewEmpty.setVisibility(View.INVISIBLE);
        recyclerView = view.findViewById(R.id.choice_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), preferences.getInt(SETTING_GALLERY_LAYOUT, 2));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavoriteAdapter(presenter.getFavoriteWallpapersList());
        recyclerView.setAdapter(adapter);
        adapter.clear();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestFavoriteList();
        log("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        uploadAdapterAndLayoutManager();
    }

    private void uploadAdapterAndLayoutManager() {
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), preferences.getInt(SETTING_GALLERY_LAYOUT, 2));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavoriteAdapter(presenter.getFavoriteWallpapersList());
    }

    @Override
    public void adapterNotifyDataSetChanged() {
        adapter.updateDataAdapter(presenter.getFavoriteWallpapersList());
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideTextEmptyList() {
        textViewEmpty.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTextEmptyList() {
        textViewEmpty.setVisibility(View.VISIBLE);
    }

    private void log(String s) {
        Log.i("MyTAG", s);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        log("onAttachFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDestroy");
    }
}
