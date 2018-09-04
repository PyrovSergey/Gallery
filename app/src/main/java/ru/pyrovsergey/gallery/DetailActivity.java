package ru.pyrovsergey.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.presenters.DetailPresenter;
import ru.pyrovsergey.gallery.presenters.contracts.DetailViewContract;

public class DetailActivity extends MvpAppCompatActivity implements DetailViewContract {

    private static final int UI_ANIMATION_DELAY = 300;
    private static final String DATE_FORMAT = "ddMMyyyy_HHmmss";
    private static final String SHARE_TYPE = "text/html";
    public static final String KEY_FAVORITE_WALLPAPER_OBJECT = "ru.pyrovsergey.gallery_key_favorite_wallpaper_object";
    private final Handler mHideHandler = new Handler();

    private FavoriteWallpaper favoriteWallpaper;
    private Toast toast;
    private boolean isInBookmarks;
    private boolean isLandscape;

    @BindView(R.id.detail_progress_bar)
    ProgressBar detailProgressBar;
    @InjectPresenter
    DetailPresenter presenter;
    @BindView(R.id.detail_head_frame_layout)
    FrameLayout detailHeadFrameLayout;
    @BindView(R.id.button_bookmark)
    ImageView buttonBookmark;
    @BindView(R.id.button_share)
    ImageView buttonShare;
    @BindView(R.id.button_download)
    ImageView buttonDownload;
    @BindView(R.id.button_full_screen)
    ImageView buttonFullScreen;
    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.button_back)
    ImageView buttonBack;
    @BindView(R.id.title_author_text_view)
    TextView titleAuthorTextView;
    @BindView(R.id.author_text_view)
    TextView authorTextView;
    @BindView(R.id.button_set_as_wallpaper)
    TextView buttonSetAsWallpaper;
    @BindView(R.id.upper_linear_layout)
    View upperLinearLayout;
    @BindView(R.id.bottom_linear_layout)
    View bottomLinearLayout;
    @BindView(R.id.button_orientation)
    ImageView buttonOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mVisible = true;
        Intent intent = getIntent();
        favoriteWallpaper = intent.getParcelableExtra(KEY_FAVORITE_WALLPAPER_OBJECT);

        //isAddedToBookmarks(favoriteWallpaper.getId());
        presenter.isAddToBookmarks(favoriteWallpaper.getId());

        if (TextUtils.isEmpty(favoriteWallpaper.getAuthor())) {
            titleAuthorTextView.setVisibility(View.INVISIBLE);
        } else {
            authorTextView.setText(favoriteWallpaper.getAuthor());
        }
        //Glide.with(this).load(favoriteWallpaper.getUrl()).into(detailImage);
        showWallpaper(favoriteWallpaper.getPortraitUrl());
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            detailImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            upperLinearLayout.setVisibility(View.VISIBLE);
            bottomLinearLayout.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public static void startDetailActivity(FavoriteWallpaper favoriteWallpaper) {
        Context context = App.getInstance().getContext();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_FAVORITE_WALLPAPER_OBJECT, favoriteWallpaper);
        context.startActivity(intent);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @OnClick({R.id.button_back, R.id.button_set_as_wallpaper, R.id.detail_image, R.id.button_full_screen,
            R.id.button_download, R.id.button_share, R.id.button_bookmark, R.id.button_orientation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.button_set_as_wallpaper:
                presenter.onClickSetWallpaper();
                break;
            case R.id.detail_image:
                show();
                break;
            case R.id.button_full_screen:
                toggle();
                break;
            case R.id.button_download:
                presenter.checkStoragePermission();
                break;
            case R.id.button_share:
                presenter.onClickShare();
                break;
            case R.id.button_bookmark:
                addOrRemoveBookmark(favoriteWallpaper);
                break;
            case R.id.button_orientation:
                getLandscapeOrPortraitWallpaper();
                break;
        }
    }

    private void getLandscapeOrPortraitWallpaper() {
        if (App.isInternetAvailable()) {
            if (!isLandscape) {
                buttonOrientation.setImageResource(R.drawable.portrait);
                isLandscape = true;
                showWallpaper(favoriteWallpaper.getLandscapeUrl());
            } else {
                buttonOrientation.setImageResource(R.drawable.landscape);
                isLandscape = false;
                showWallpaper(favoriteWallpaper.getPortraitUrl());
            }
        } else {
            presenter.noInternetConnection();
        }
    }

    @Override
    public void showSetWallpaperAlertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_message_install_this_wallpaper)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                presenter.setWallpaper();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void setWallpaper() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) detailImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(bitmap);
            wallpaperManager.suggestDesiredDimensions(width, height);
            makeToast(getString(R.string.wallpapers_changed));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        if (!mVisible) {
            detailImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            mVisible = true;

            // Schedule a runnable to display UI elements after a delay
            mHideHandler.removeCallbacks(mHidePart2Runnable);
            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        }
    }

    private void hide() {
        upperLinearLayout.setVisibility(View.GONE);
        bottomLinearLayout.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    public void checkStoragePermissionGrantedAndDownload() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("MyTAG", "checkStoragePermissionGrantedAndDownload() - Permission is granted");
                presenter.onClickDownloadWallpaper();
            } else {
                Log.d("MyTAG", "checkStoragePermissionGrantedAndDownload() - Permission is revoked");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Log.d("MyTAG", "checkStoragePermissionGrantedAndDownload() - Permission is granted");
            presenter.onClickDownloadWallpaper();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("MyTAG", "onRequestPermissionsResult() - Permission: " + permissions[0] + " was " + grantResults[0]);
            presenter.onClickDownloadWallpaper();
        } else {
            makeToast(getString(R.string.message_to_obtain_permission));
        }
    }

    @Override
    public void downloadWallpaper() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) detailImage.getDrawable();
        Bitmap image = bitmapDrawable.getBitmap();
        String savedImagePath;

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String timestamp = simpleDateFormat.format(date);

        String imageFileName = "JPEG_" + timestamp + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/MyWallpapers");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            makeToast(getString(R.string.image_saved));
        }
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    public void share() {
        if (App.isInternetAvailable()) {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType(SHARE_TYPE);
            intentShare.putExtra(Intent.EXTRA_TEXT,
                    (getString(R.string.photos_provided_by_pexels) + " ") + favoriteWallpaper.getLandscapeUrl());
            Intent chooser = Intent.createChooser(intentShare, getString(R.string.look_at_that));
            if (intentShare.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        } else {
            presenter.noInternetConnection();
        }
    }

    private void addOrRemoveBookmark(FavoriteWallpaper favorite) {
        if (!isInBookmarks) {
            presenter.insertBookmark(favorite);
        } else {
            presenter.deleteBookmark(favorite);
        }
    }

    private void showWallpaper(String url) {
        detailProgressBar.setVisibility(View.VISIBLE);
        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                detailHeadFrameLayout.setVisibility(View.VISIBLE);
                detailProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(detailImage);
    }

    @Override
    public void positiveResultCheckIsAddToBookmarks() {
        buttonBookmark.setImageResource(R.drawable.bookmark);
        isInBookmarks = true;
    }

    @Override
    public void onSuccessDeleteBookmark() {
        isInBookmarks = false;
        buttonBookmark.setImageResource(R.drawable.bookmark_border);
    }

    @Override
    public void onSuccessInsertBookmark() {
        isInBookmarks = true;
        buttonBookmark.setImageResource(R.drawable.bookmark);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void showToastMessage(String message) {
        makeToast(message);
    }

    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showNoConnectionDialogMessage() {
        Toasty.info(App.getInstance(), App.getInstance().getString(R.string.no_internet_connection) +
                "\n" + App.getInstance().getString(R.string.check_connection_settings), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (toast != null) {
            toast.cancel();
        }
    }
}
