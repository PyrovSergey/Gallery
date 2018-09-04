package ru.pyrovsergey.gallery;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.fragments.FavoriteFragment;
import ru.pyrovsergey.gallery.fragments.ListOfSelectedTopicsFragment;
import ru.pyrovsergey.gallery.fragments.ListThemeFragment;
import ru.pyrovsergey.gallery.presenters.HeadPresenter;
import ru.pyrovsergey.gallery.presenters.contracts.HeadContract;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HeadContract {
    public static final String SETTING_GALLERY_LAYOUT = "setting_gallery_layout";
    @InjectPresenter
    HeadPresenter headPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu_black_24dp);
        toolbar.setOverflowIcon(drawable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            startOrReplaceListThemeFragment();
        }
    }

    @Override
    protected void onResume() {
        if (!App.isInternetAvailable()) {
            headPresenter.noInternetConnection();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("ListOfSelectedTopics") != null) {
            startOrReplaceListThemeFragment();
            if (!App.isInternetAvailable()) {
                headPresenter.noInternetConnection();
            }

        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        ImageView searchCloseIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_close_black_24dp);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorBlack));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black_overlay));

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                headPresenter.searchWallpapers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    startOrReplaceListThemeFragment();
                }
                return false;
            }
        });

        searchView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                //Toast.makeText(App.getInstance().getContext(), String.valueOf(visibility), Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings_two_line) {
            editor.putInt(SETTING_GALLERY_LAYOUT, 2);
            editor.apply();
            return true;
        }
        if (id == R.id.action_settings_three_line) {
            editor.putInt(SETTING_GALLERY_LAYOUT, 3);
            editor.apply();
            return true;
        }

        if (id == R.id.action_settings_four_line) {
            editor.putInt(SETTING_GALLERY_LAYOUT, 4);
            editor.apply();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_gallery:
                startOrReplaceListThemeFragment();
                break;
            case R.id.nav_about_gallery:
                headPresenter.callAboutGallery();
                break;
            case R.id.nav_my_favorite:
                startOrReplaceFavoriteFragment();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startListOfSelectedTopicsFragment(String query) {
        startOrReplaceFragmentListOfSelectedTopics(query);
    }

    @Override
    public void showAboutGalleryMessage() {
        showAboutMessage();
    }

    private void startOrReplaceListThemeFragment() {
        toolbarTitle.setText(R.string.app_name);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListThemeFragment fragment = App.getComponent().getListThemeFragment();
        ft.replace(R.id.frame, fragment, "ListThemeFragment");
        ft.commitAllowingStateLoss();
        if (!App.isInternetAvailable()) {
            headPresenter.noInternetConnection();
        }
    }

    private void startOrReplaceFragmentListOfSelectedTopics(String query) {
        if (App.isInternetAvailable()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ListOfSelectedTopicsFragment fragment = ListOfSelectedTopicsFragment.getInstance(query);
            ft.replace(R.id.frame, fragment, "ListOfSelectedTopics");
            ft.commitAllowingStateLoss();
            if (toolbarTitle != null && !TextUtils.isEmpty(query)) {
                toolbarTitle.setText(query);
            }
        } else {
            headPresenter.noInternetConnection();
        }
    }

    private void startOrReplaceFavoriteFragment() {
        if (App.isInternetAvailable()) {
            toolbarTitle.setText(R.string.my_favorite_wallpapers);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FavoriteFragment fragment = FavoriteFragment.getInstance();
            ft.replace(R.id.frame, fragment, "FavoriteFragment");
            ft.commitAllowingStateLoss();
        } else {
            headPresenter.noInternetConnection();
        }
    }

    private void showAboutMessage() {
        final SpannableString s = new SpannableString(getString(R.string.about_text));
        Linkify.addLinks(s, Linkify.ALL);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
            builder.setView(R.layout.dialog_alert_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue to do
                        }
                    })
                    .show();
        } else {
            builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name)
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage(R.string.about_text)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue to do
                        }
                    })
                    .show();
        }
    }

    @Override
    public void showNoConnectionDialogMessage() {
        Toasty.info(App.getInstance(), App.getInstance().getString(R.string.no_internet_connection) +
                "\n" + App.getInstance().getString(R.string.check_connection_settings), Toast.LENGTH_SHORT, true).show();
    }
}
