package ru.pyrovsergey.gallery.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.presenter.MainFragmentPresenter;
import ru.pyrovsergey.gallery.presenter.MainListView;

public class MainFragment extends MvpAppCompatFragment implements MainListView, FragmentAdapterListener {
    @InjectPresenter(type = PresenterType.GLOBAL)
    MainFragmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main_list, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getScreenOrientation(), StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MainFragmentAdapter(presenter.getMainListWallpaper()));
        return recyclerView;
    }

    private int getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return 2;
        } else {
            return 4;
        }
    }

    @Override
    public void onClickItem(String theme) {

    }

    @Override
    public void onClickListener(String s) {
        presenter.searchWallpapers(s);
    }
}