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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
    private ProgressBar paginationProgressBar;
    private String query;
    private RecyclerView recyclerView;
    private ListOfSelectedTopicsAdapter adapter;
    private GridLayoutManager layoutManager;
    private Boolean loading = false;
    private int pageCount = 1;
    private boolean isLastPage = false;
    private static boolean isFirstStart = true;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_list, container, false);
        paginationProgressBar = view.findViewById(R.id.choice_list_pagination_progress_bar);
        recyclerView = view.findViewById(R.id.choice_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), getScreenOrientation());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListOfSelectedTopicsAdapter(presenter.getPhotosItemList());
        recyclerView.setAdapter(adapter);
        adapter.clear();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() - 6;
                if (lastVisibleItemPosition == adapter.getItemCount() - 7) {
                    if (!loading && !isLastPage) {
                        showProgressBar();
                        loading = true;
                        presenter.searchWallpapers(query, pageCount++);
                    }
                }
            }
        });
        return view;
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
        hideProgressBar();
        isFirstStart = false;
        loading = false;
        adapter.updateDataAdapter(presenter.getPhotosItemList());
    }

    @Override
    public void onErrorLoadOfLastPage() {
        hideProgressBar();
        isLastPage = true;
    }

    private void showProgressBar() {
        if (isFirstStart) {
            paginationProgressBar.setVisibility(View.INVISIBLE);
        } else {
            paginationProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.66f));
            paginationProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (isFirstStart) {
            paginationProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0f));
            paginationProgressBar.setVisibility(View.INVISIBLE);
            isFirstStart = false;
        } else {
            paginationProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0f));
            paginationProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
