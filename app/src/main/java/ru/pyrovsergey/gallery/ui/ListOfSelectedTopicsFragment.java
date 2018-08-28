package ru.pyrovsergey.gallery.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.presenter.ListOfSelectedTopicsContract;
import ru.pyrovsergey.gallery.presenter.ListOfSelectedTopicsFragmentPresenter;

public class ListOfSelectedTopicsFragment extends MvpAppCompatFragment implements ListOfSelectedTopicsContract {
    private static final String KEY_QUERY = "ru.pyrovsergey.gallery.ui_key_query";

    @InjectPresenter
    ListOfSelectedTopicsFragmentPresenter presenter;
    private ProgressBar progressBar;
    private String query;
    private RecyclerView recyclerView;
    private ListOfSelectedTopicsAdapter adapter;
    //private StaggeredGridLayoutManager layoutManager;
    private GridLayoutManager layoutManager;
    private Boolean loading = false;
    private int pageCount = 1;
    private boolean isLastPage = false;

    public static ListOfSelectedTopicsFragment getInstance(String query) {
        ListOfSelectedTopicsFragment fragment = new ListOfSelectedTopicsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_QUERY, query);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = this.getArguments().getString(KEY_QUERY);
        presenter.searchWallpapers(query, pageCount++);
//        loading = true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main_list, container, false);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new StaggeredGridLayoutManager(getScreenOrientation(), StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), getScreenOrientation());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListOfSelectedTopicsAdapter(presenter.getPhotosItemList());
        recyclerView.setAdapter(adapter);
        adapter.clear();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == adapter.getItemCount() - 1) {
                    if (!loading && !isLastPage) {
                        loading = true;
                        presenter.searchWallpapers(query, pageCount++);
                    }
                }
            }
        });
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
    public void adapterNotifyDataSetChanged() {
        loading = false;
        Toast.makeText(App.getInstance().getContext(), "Number of page " + String.valueOf(pageCount), Toast.LENGTH_SHORT).show();
        adapter.updateDataAdapter(presenter.getPhotosItemList());
    }

    @Override
    public void onErrorLoadOfLastPage() {
        isLastPage = true;
    }
}
