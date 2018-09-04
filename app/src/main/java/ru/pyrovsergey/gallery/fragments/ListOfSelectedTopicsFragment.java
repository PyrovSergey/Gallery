package ru.pyrovsergey.gallery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import es.dmoral.toasty.Toasty;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.fragments.adapters.ListOfSelectedTopicsAdapter;
import ru.pyrovsergey.gallery.presenters.ListOfSelectedTopicsFragmentPresenter;
import ru.pyrovsergey.gallery.presenters.contracts.ListOfSelectedTopicsContract;

public class ListOfSelectedTopicsFragment extends MvpAppCompatFragment
        implements ListOfSelectedTopicsContract, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String KEY_QUERY = "ru.pyrovsergey.gallery.ui_key_query";
    private static final String SETTING_GALLERY_LAYOUT = "setting_gallery_layout";

    @InjectPresenter
    ListOfSelectedTopicsFragmentPresenter presenter;

    private ProgressBar paginationProgressBar;
    private ProgressBar progressBar;
    private TextView textViewEmpty;
    private String query;
    private RecyclerView recyclerView;
    private ListOfSelectedTopicsAdapter adapter;
    private GridLayoutManager layoutManager;
    private SharedPreferences preferences;
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
        progressBar = view.findViewById(R.id.choice_list_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        recyclerView = view.findViewById(R.id.choice_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), preferences.getInt(SETTING_GALLERY_LAYOUT, 2));
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

    @Override
    public void onErrorLoadOfLastPage() {
        hideProgressBar();
        isLastPage = true;
        if (!App.isInternetAvailable()) {
            showNoConnectionDialogMessage();
        }
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
            progressBar.setVisibility(View.INVISIBLE);
            paginationProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0f));
            paginationProgressBar.setVisibility(View.INVISIBLE);
            isFirstStart = false;
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            paginationProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0f));
            paginationProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        uploadAdapterAndLayoutManager();
    }

    @Override
    public void adapterNotifyDataSetChanged() {
        hideProgressBar();
        isFirstStart = false;
        loading = false;
        adapter.updateDataAdapter(presenter.getPhotosItemList());
    }

    private void uploadAdapterAndLayoutManager() {
        layoutManager = new GridLayoutManager(App.getInstance().getContext(), preferences.getInt(SETTING_GALLERY_LAYOUT, 2));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListOfSelectedTopicsAdapter(presenter.getPhotosItemList());
    }

    private void showNoConnectionDialogMessage() {
        Toasty.error(App.getInstance(), App.getInstance().getString(R.string.no_internet_connection) +
                "\n" + App.getInstance().getString(R.string.check_connection_settings), 0, true).show();
    }
}
