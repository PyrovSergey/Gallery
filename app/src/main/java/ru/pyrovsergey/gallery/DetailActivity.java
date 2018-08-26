package ru.pyrovsergey.gallery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.pyrovsergey.gallery.app.App;

public class DetailActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.button_back)
    ImageView buttonBack;
    @BindView(R.id.title_author_text_view)
    TextView titleAuthorTextView;
    @BindView(R.id.author_text_view)
    TextView authorTextView;

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
    @BindView(R.id.button_set_as_wallpaper)
    TextView buttonSetAsWallpaper;
    @BindView(R.id.button_full_screen)
    ImageView buttonFullScreen;
    @BindView(R.id.upper_linear_layout)
    View upperLinearLayout;
    @BindView(R.id.bottom_linear_layout)
    View bottomLinearLayout;

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

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mVisible = true;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Set up the user interaction to manually show or hide the system UI.
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mVisible) {
                    show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        String url = getIntent().getStringExtra("Key");
        String author = getIntent().getStringExtra("Author");
        if (TextUtils.isEmpty(author)) {
            titleAuthorTextView.setVisibility(View.INVISIBLE);
        } else {
            authorTextView.setText(author);
        }
        Glide.with(this).load(url).into(detailImage);
    }

    public static void startDetailActivity(String url, String author) {
        Context context = App.getInstance().getContext();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Key", url);
        intent.putExtra("Author", author);
        context.startActivity(intent);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
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

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        detailImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void setWallpaperAlertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_message_install_this_wallpaper)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.alert_message_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                setWallpaper();
                            }
                        })
                .setNegativeButton(getString(R.string.alert_message_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setWallpaper() {
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

    private void makeToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "DetailActivity" + message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @OnClick({R.id.button_back, R.id.button_set_as_wallpaper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.button_set_as_wallpaper:
                setWallpaperAlertMessage();
                break;
        }
    }

    @OnClick(R.id.button_full_screen)
    public void onViewClicked() {
        toggle();
    }
}
