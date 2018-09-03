package ru.pyrovsergey.gallery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import ru.pyrovsergey.gallery.MainActivity;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.fragments.adapters.ListThemeFragmentAdapter;
import ru.pyrovsergey.gallery.presenters.contracts.ListThemeContract;
import ru.pyrovsergey.gallery.presenters.ListThemeFragmentPresenter;
import ru.pyrovsergey.gallery.presenters.listeners.ListThemeFragmentAdapterListener;


public class ListThemeFragment extends MvpAppCompatFragment implements ListThemeContract, ListThemeFragmentAdapterListener, SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SETTING_GALLERY_LAYOUT = "setting_gallery_layout";
    @InjectPresenter
    ListThemeFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private ListThemeFragmentAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
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
//        Log.i("MyTAG", "сработал onClickListener ListThemeFragment на Теме - " + query);
    }


    private void startListOfSelectedTopicsAdapter(String query) {
 //       Log.i("MyTAG", "сработал onClickListener startListOfSelectedTopicsAdapter на Теме - " + query);
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
        ListOfSelectedTopicsFragment fragment = ListOfSelectedTopicsFragment.getInstance(query);
        ft.replace(R.id.frame, fragment, "ListOfSelectedTopics");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        uploadAdapterAndLayoutManager();
    }

    private void uploadAdapterAndLayoutManager() {
        layoutManager = new StaggeredGridLayoutManager(preferences.getInt(SETTING_GALLERY_LAYOUT, 2), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListThemeFragmentAdapter(presenter.getMainListWallpaper());
    }
}