package ru.pyrovsergey.gallery;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.pyrovsergey.gallery.presenter.MainFragmentPresenter;
import ru.pyrovsergey.gallery.presenter.MainListView;

public class MainFragment extends MvpAppCompatFragment implements MainListView {
    @InjectPresenter
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
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), getScreenOrientation()));
        log(String.valueOf(presenter.getMainListWallpaper().size()));
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

    private void log(String log) {
        Log.i("MyTAG", log);
    }
}
