package ru.pyrovsergey.gallery.ui;

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

import java.util.Objects;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.presenter.ListThemeContract;
import ru.pyrovsergey.gallery.presenter.ListThemeFragmentPresenter;

public class ListThemeFragment extends MvpAppCompatFragment implements ListThemeContract, ListThemeFragmentAdapterListener {

    @InjectPresenter
    ListThemeFragmentPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main_list, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getScreenOrientation(), StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new ListThemeFragmentAdapter(presenter.getMainListWallpaper()));
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
    public void onShowMessage(String message) {
        Toast.makeText(App.getInstance().getContext(), "ListThemeFragment" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickListener(String query) {
        startListOfSelectedTopicsAdapter(query);
    }

    private void startListOfSelectedTopicsAdapter(String query) {
        FragmentTransaction ft = null;
        try {
            ft = getActivity().getSupportFragmentManager().beginTransaction();
        } catch (NullPointerException e) {
            // TO_DO
        }
        ListOfSelectedTopicsFragment fragment = ListOfSelectedTopicsFragment.getInstance(query);
        if (ft != null) {
            ft.add(R.id.frame, fragment);
            ft.addToBackStack("ListOfSelectedTopics");
            ft.commit();
        }
    }
}
