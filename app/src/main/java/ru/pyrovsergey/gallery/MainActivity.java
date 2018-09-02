package ru.pyrovsergey.gallery;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.ui.FavoriteFragment;
import ru.pyrovsergey.gallery.ui.ListOfSelectedTopicsFragment;
import ru.pyrovsergey.gallery.ui.ListThemeFragment;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyContract {
    public static final String SETTING_GALLERY_LAYOUT = "setting_gallery_layout";
    @InjectPresenter
    Presenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private FragmentTransaction transaction;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame, App.getComponent().getListThemeFragment()).commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("ListOfSelectedTopics") != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, App.getComponent().getListThemeFragment()).commitAllowingStateLoss();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchWallpapers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
                return false;
            }
        });

        searchView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Toast.makeText(App.getInstance().getContext(), String.valueOf(visibility), Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ListThemeFragment fragment = new ListThemeFragment();
                ft.replace(R.id.frame, fragment, "ListThemeFragment");
                ft.commitAllowingStateLoss();
                break;
            case R.id.nav_about_gallery:
                presenter.callAboutGallery();
                break;
            case R.id.nav_my_favorite:
                startFavoriteFragment();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void startListOfSelectedTopicsFragment(String query) {
        starFragmentListOfSelectedTopics(query);
    }

    @Override
    public void showAboutGalleryMessage() {
        showAboutMessage();
    }

    private void starFragmentListOfSelectedTopics(String query) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListOfSelectedTopicsFragment fragment = ListOfSelectedTopicsFragment.getInstance(query);
        ft.replace(R.id.frame, fragment, "ListOfSelectedTopics");
        ft.commitAllowingStateLoss();
    }

    private void startFavoriteFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FavoriteFragment fragment = FavoriteFragment.getInstance();
        ft.replace(R.id.frame, fragment, "FavoriteFragment");
        ft.commitAllowingStateLoss();
    }

}
