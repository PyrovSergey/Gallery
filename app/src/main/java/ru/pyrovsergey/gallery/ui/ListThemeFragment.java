package ru.pyrovsergey.gallery.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.presenter.ListThemeContract;
import ru.pyrovsergey.gallery.presenter.ListThemeFragmentPresenter;

public class ListThemeFragment extends MvpAppCompatFragment implements ListThemeContract, ListThemeFragmentAdapterListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @InjectPresenter
    ListThemeFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private ListThemeFragmentAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main_list, container, false);
        recyclerView.setHasFixedSize(true);
        uploadAdapterAndLayoutManager();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onShowMessage(String message) {
        Toast.makeText(App.getInstance().getContext(), "ListThemeFragment" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickListener(String query) {
        startListOfSelectedTopicsAdapter(query);
    }

    private void startListOfSelectedTopicsAdapter(String query) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ListOfSelectedTopicsFragment fragment = ListOfSelectedTopicsFragment.getInstance(query);
        ft.add(R.id.frame, fragment);
        ft.addToBackStack("ListOfSelectedTopics");
        ft.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        uploadAdapterAndLayoutManager();
    }

    private void uploadAdapterAndLayoutManager() {
        layoutManager = new StaggeredGridLayoutManager(preferences.getInt("setting_gallery_layout", 2), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListThemeFragmentAdapter(presenter.getMainListWallpaper());
    }
}
